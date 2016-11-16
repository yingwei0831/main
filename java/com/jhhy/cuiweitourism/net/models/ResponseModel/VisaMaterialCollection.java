package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by birney1 on 16/11/15.
 */
public class VisaMaterialCollection implements Serializable {
       public ArrayList<VisaMaterial> student;
       public ArrayList<VisaMaterial>  children;
       public ArrayList<VisaMaterial>  freedom;
       public ArrayList<VisaMaterial>  retired;
    public ArrayList<VisaMaterial>  worker;

    @Override
    public String toString() {
        return "VisaMaterialCollection{" +
                "student=" + student +
                ", children=" + children +
                ", freedom=" + freedom +
                ", retired=" + retired +
                ", worker=" + worker +
                '}';
    }
}
