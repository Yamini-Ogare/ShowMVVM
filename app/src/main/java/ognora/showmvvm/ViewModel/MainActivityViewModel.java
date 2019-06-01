package ognora.showmvvm.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.widget.Toast;


import java.io.File;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

import ognora.showmvvm.Model.FileModel;
import ognora.showmvvm.Room.FileDatabase;


public class MainActivityViewModel extends AndroidViewModel {

    Bundle bundle ;
    ArrayList<FileModel> arrayList = new ArrayList<>();
    private MutableLiveData<ArrayList<FileModel>> arrayListMutableLiveData = new MutableLiveData<>();
    int i;
    boolean success = true;
    String Fname ;

    FileDatabase fileDatabase ;


    public MainActivityViewModel(@NonNull Application application) {

        super(application);
        fileDatabase = FileDatabase.getDatabase(application);
    }


    public LiveData<ArrayList<FileModel>> openfolder() {

        ArrayList<FileModel> roomfileModels = new ArrayList<>(Arrays.asList(fileDatabase.fileDao().getFolder()));
        arrayListMutableLiveData.setValue(roomfileModels);

        if(roomfileModels.size() == 0) {

            arrayList = new ArrayList<>();

            // get access to the folder named Show

            File storageDir = new File(Environment.getExternalStorageDirectory(), "Show");

            // if Folder does not exist then create folder
            if (!storageDir.exists()) {
                success = storageDir.mkdir();
                Toast.makeText(getApplication().getBaseContext(), "Folder created", Toast.LENGTH_LONG).show();
            }

            if (success) {
                // Access the storage path and get all its file

                File f = new File(storageDir.getPath());
                File file[] = f.listFiles();


                if (file.length > 0) {

                    for (i = 0; i < file.length; i++) {
                        FileModel fModel = new FileModel();
                        fModel.setFile(file[i].getAbsoluteFile());
                        fModel.setParentid(-1);

                        arrayList.add(fModel);
                        fileDatabase.fileDao().addFile(fModel);

                    }
                }

                if (arrayList.size() == 0) {
                    Toast.makeText(getApplication().getBaseContext(), "Folder is empty", Toast.LENGTH_LONG).show();
                }

                arrayListMutableLiveData.setValue(arrayList);

            } else {
                Toast.makeText(getApplication().getBaseContext(), "Error encountered", Toast.LENGTH_LONG).show();
            }
        }
        return arrayListMutableLiveData;
    }


    // writing data from bundle

    public void writeTo(Bundle bundle) {

        this.bundle = bundle ;
    }


    // function to get image files.
    public LiveData<ArrayList<FileModel>> getImages() {


        arrayList = new ArrayList<>();


        if (bundle != null) {
            File folder = new File(bundle.getString("File"));
            int folderid = bundle.getInt("Folderid");
            Fname = folder.getName();


            ArrayList<FileModel> roomfileModels = new ArrayList<>(Arrays.asList(fileDatabase.fileDao().getImages(folderid)));
            arrayListMutableLiveData.setValue(roomfileModels);

            if (roomfileModels.size() == 0) {
                // textview.setText(folder.getName());


                File file[] = folder.listFiles();

                if (file.length > 0) {

                    for (int i = 0; i < file.length; i++) {

                        FileModel fModel = new FileModel();
                        fModel.setFile(file[i].getAbsoluteFile());
                        fModel.setParentid(folderid);
                        arrayList.add(fModel);

                        fileDatabase.fileDao().addFile(fModel);
                    }


                }

                //Make toast when the folder is empty

                if (arrayList.size() == 0) {
                    Toast.makeText(getApplication().getBaseContext(), "No Image Files", Toast.LENGTH_LONG).show();
                }

                arrayListMutableLiveData.setValue(arrayList);
            }
        }

        return  arrayListMutableLiveData;
    }

    public LiveData<String> getname(){

        MutableLiveData<String> name = new MutableLiveData<>() ;
        name.setValue(Fname);
        return  name;

    }

    public void del(FileModel fileModel)
    {
        fileDatabase.fileDao().deleteFile(fileModel);

    }

}
