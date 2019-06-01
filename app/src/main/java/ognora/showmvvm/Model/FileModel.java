package ognora.showmvvm.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.File;

@Entity(tableName = "FileTable")
public class FileModel {

    @ColumnInfo
    String name;
    @ColumnInfo
    String path ;

    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo
    int parentid;

    public FileModel(File file) {
        this.name = file.getName();
        this.path = file.getPath();
    }

    public FileModel() {

    }

    public String getName() {
        return name;
    }

    public File getFile(){ return new File(path); }


    public String getPath() {
        return path;
    }

    public void setFile(File file) {
        this.name = file.getName();
        this.path = file.getPath();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }
}
