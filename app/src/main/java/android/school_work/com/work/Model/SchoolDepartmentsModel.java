package android.school_work.com.work.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.school_work.com.work.Helper.AppConfig;
import android.school_work.com.work.Helper.WebServices;
import android.school_work.com.work.R;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SchoolDepartmentsModel implements Parcelable {
    public static final int NORMAL_TYPE = 0;
    //creator - used when un-parceling our parcle (creating the object)
    public static final Creator<SchoolDepartmentsModel> CREATOR = new Creator<SchoolDepartmentsModel>() {

        @Override
        public SchoolDepartmentsModel createFromParcel(Parcel parcel) {
            return new SchoolDepartmentsModel(parcel);
        }

        @Override
        public SchoolDepartmentsModel[] newArray(int size) {
            return new SchoolDepartmentsModel[0];
        }
    };
    private static final String
            KEY_id = "id",
            KEY_name = "name",
            KEY_description = "description",
            KEY_location = "location",
            KEY_phoneSec = "phoneSec",
            KEY_photoUrl = "photoUrl",
            KEY_purpose = "purpose",
            KEY_Type = "Type";
    private static final String TAG = SchoolDepartmentsModel.class.getSimpleName();
    private String name, phoneSec, description, location, photoUrl, purpose;
    private int Type;
    private long id;

    public SchoolDepartmentsModel() {
    }

    public SchoolDepartmentsModel(
            long id,
            String name,
            String phoneSec,
            String description,
            String location,
            String photoUrl,
            String purpose,
            int Type
    ) {
        this.id = id;
        this.name = name;
        this.phoneSec = phoneSec;
        this.description = description;
        this.location = location;
        this.photoUrl = photoUrl;
        this.purpose = purpose;
        this.Type = Type;
    }


    public SchoolDepartmentsModel(Parcel parcel) {
        this.id = parcel.readLong();
        this.name = parcel.readString();
        this.phoneSec = parcel.readString();
        this.description = parcel.readString();
        this.location = parcel.readString();
        this.photoUrl = parcel.readString();
        this.purpose = parcel.readString();
        this.Type = parcel.readInt();
    }

    public static void getSchoolDepartments(final SchoolDepartmentsModel.getProviderData callback) {
        URL _url = null;
        try {
            _url = new URL(AppConfig.URL_LIST);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        WebServices.callJsonResponse(String.valueOf(_url), new WebServices.JsonResponse() {
            @Override
            public void onSuccess(JSONArray data) {
                Log.i("callJsonResponse ", "SUCCESS");
                SchoolDepartmentsModel item;
                ArrayList<SchoolDepartmentsModel> membersList = new ArrayList<>();
                for (int i = 0; i < data.length(); i++) {

                    JSONObject row = null;
                    try {
                        row = data.getJSONObject(i);

                        item = new SchoolDepartmentsModel(
                                row.getLong(SchoolDepartmentsModel.KEY_id),
                                row.getString(SchoolDepartmentsModel.KEY_name),
                                row.getString(SchoolDepartmentsModel.KEY_phoneSec),
                                row.getString(SchoolDepartmentsModel.KEY_description),
                                row.getString(SchoolDepartmentsModel.KEY_location),
                                row.getString(SchoolDepartmentsModel.KEY_photoUrl),
                                row.getString(SchoolDepartmentsModel.KEY_purpose),
                                0
                        );
                        membersList.add(item);

                    } catch (JSONException e) {
//                            e.printStackTrace();
                        callback.onFailure("An error occurred during parsing data.", false, 1);
                    }
                }
                callback.onSuccess(membersList);

            }
        }, new WebServices.ErrorResponse() {
            @Override
            public void onError(boolean state) {
                Log.i(TAG, String.valueOf(state));
                callback.onFailure("An error occurred with internet connection.", state, 0);
            }
        });
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.phoneSec);
        parcel.writeString(this.description);
        parcel.writeString(this.location);
        parcel.writeString(this.photoUrl);
        parcel.writeString(this.purpose);
        parcel.writeInt(this.Type);
    }

    /**
     * get methods
     */

    public String getName() {
        return this.name;
    }

    public String getPhotoUrl(){
        return this.photoUrl;
    }
    public String getPhoneSec() {
        return this.phoneSec;
    }

    public String getDescription() {
        return this.description;
    }

    public String getPurpose(){
        return this.purpose;
    }
    public String getLocation(){
        return this.location;
    }
    public int getType() {
        return this.Type;
    }

    public interface getProviderData {
        void onSuccess(ArrayList<SchoolDepartmentsModel> result);

        void onFailure(String message, boolean state, int code);
    }

}
