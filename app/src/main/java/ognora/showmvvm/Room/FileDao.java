package ognora.showmvvm.Room;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import ognora.showmvvm.Model.FileModel;

@Dao
public interface FileDao {

     @Insert
    void addFile(FileModel fileModel);

     @Delete
     void deleteFile(FileModel fileModel);

     @Query("SELECT * from FileTable where parentid=-1")
     FileModel[] getFolder();

     @Query("SELECt * from FileTable where parentid= :pid")
     FileModel[]  getImages(int pid);


}
