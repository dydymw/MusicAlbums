package com.tdme.android.musicalbums.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.Toast;

import com.tdme.android.musicalbums.Myapplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/6.
 */

public class Crop {
    private List<String> list;
    private List<String> filepaths;


    public Crop(List<String> list) {
        this.list = list;
    }

    public List<String> cropmr() {
        int i = 0;
        String[] a;
        filepaths = new ArrayList<>();
        while (i <list.size()) {
            filepaths.add(mr1(list.get(i)));
            i++;
            if (i < list.size())
                filepaths.add(mr2(list.get(i)));
            else
                continue;
            i++;
            if (i < list.size())
                filepaths.add(mr3(list.get(i)));
            else
                continue;
            i++;
            if (i < list.size())
                filepaths.add(mr4(list.get(i)));
            else continue;
            i++;
            if (i < list.size())
                filepaths.add(mr5(list.get(i)));
            else continue;
            i++;
            if (i + 1 < list.size()) {
                a = mr6(list.get(i), list.get(i + 1));
                filepaths.add(a[0]);
                filepaths.add(a[1]);
            } else continue;
            i = i + 2;
            if (i + 1 < list.size()) {
                a = mr7(list.get(i), list.get(i + 1));
                filepaths.add(a[0]);
                filepaths.add(a[1]);
            } else continue;
            i = i + 2;
            if (i + 1 < list.size()) {
                a = mr8(list.get(i), list.get(i + 1));
                filepaths.add(a[0]);
                filepaths.add(a[1]);
            } else continue;
            i = i + 2;

        }

        return filepaths;
    }

    public List<String> cropshyf() {
        int i = 0;
        String[] a;
        filepaths = new ArrayList<>();
        while (i < list.size()) {
            filepaths.add(shyf1(list.get(i)));
            i++;
            if (i < list.size())
                filepaths.add(shyf2(list.get(i)));
            else
                continue;
            i++;
            if (i + 2 < list.size()) {
                a = shyf3(list.get(i), list.get(i + 1), list.get(i + 2));
                filepaths.add(a[0]);
                filepaths.add(a[1]);
                filepaths.add(a[2]);
            } else
                continue;
            i = i + 3;
            if (i + 1 < list.size()) {
                a = shyf4(list.get(i), list.get(i + 1));
                filepaths.add(a[0]);
                filepaths.add(a[1]);
            } else
                continue;
            i = i + 2;
            if (i + 2 < list.size()) {
                a = shyf5(list.get(i), list.get(i + 1), list.get(i + 2));
                filepaths.add(a[0]);
                filepaths.add(a[1]);
                filepaths.add(a[2]);
            } else
                continue;
            i = i + 3;
            if (i + 1 < list.size()) {
                a = shyf6(list.get(i), list.get(i + 1));
                filepaths.add(a[0]);
                filepaths.add(a[1]);

            } else
                continue;
            i = i + 2;
            if (i < list.size())
                filepaths.add(shyf7(list.get(i)));
            else
                continue;
            i++;
            if (i + 2 < list.size()) {
                a = shyf8(list.get(i), list.get(i + 1), list.get(i + 2));
                filepaths.add(a[0]);
                filepaths.add(a[1]);
                filepaths.add(a[2]);
            } else
                continue;
            i = i + 3;

            if (i < list.size())
                filepaths.add(shyf9(list.get(i)));
            else
                continue;
            i++;

            if (i < list.size())
                filepaths.add(shyf10(list.get(i)));
            else
                continue;
            i++;

            if (i < list.size())
                filepaths.add(shyf11(list.get(i)));
            else
                continue;
            i++;

            if (i < list.size())
                filepaths.add(shyf12(list.get(i)));
            else
                continue;
            i++;
        }
        return filepaths;
    }


    public String base1(String filepath, double w, double h) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap src = cropBitmap(BitmapFactory.decodeFile(filepath, options), w, h);
        String s = saveBitmap(new File(filepath).getName(), src);

        return s;
    }

    public String[] base2(String filepath1, String filepath2, double w1, double h1, double w2, double h2) {
        String s1 = base1(filepath1, w1, h1);
        String s2 = base1(filepath2, w2, h2);
        String[] s = {s1, s2};
        return s;
    }

    public String[] base3(String filepath1, String filepath2, String filepath3, double w1, double h1, double w2, double h2, double w3, double h3) {
        String s1 = base1(filepath1, w1, h1);
        String s2 = base1(filepath2, w2, h2);
        String s3 = base1(filepath3, w3, h3);
        String[] s = {s1, s2, s3};
        return s;
    }

    public String mr1(String filepath) {
        return base1(filepath, 65, 98);
    }

    public String mr2(String filepath) {
        return base1(filepath, 51, 92);
    }

    public String mr3(String filepath) {
        return base1(filepath, 52, 80);
    }

    public String mr4(String filepath) {
        return base1(filepath, 52, 80);
    }

    public String mr5(String filepath) {
        return base1(filepath, 52, 80);
    }

    public String[] mr6(String filepath1, String filepath2) {
        return base2(filepath1, filepath2, 51, 46, 51, 40);
    }

    public String[] mr7(String filepath1, String filepath2) {
        return base2(filepath1, filepath2, 51, 46, 51, 40);
    }

    public String[] mr8(String filepath1, String filepath2) {
        return base2(filepath1, filepath2, 51, 46, 51, 40);
    }

    public String shyf1(String filepath) {
        return base1(filepath, 56, 67);
    }

    public String shyf2(String filepath) {
        return base1(filepath, 65, 91);
    }

    public String[] shyf3(String filepath1, String filepath2, String filepath3) {
        return base3(filepath1, filepath2, filepath3, 27, 40, 27, 48, 27, 48);
    }

    public String[] shyf4(String filepath1, String filepath2) {
        return base2(filepath1, filepath2, 1, 1, 36, 21);
    }

    public String[] shyf5(String filepath1, String filepath2, String filepath3) {
        return base3(filepath1, filepath2, filepath3, 31, 61, 1, 1, 1, 1);
    }

    public String[] shyf6(String filepath1, String filepath2) {
        return base2(filepath1, filepath2, 52, 38, 52, 38);
    }

    public String shyf7(String filepath) {
        return base1(filepath, 55, 79);
    }

    public String[] shyf8(String filepath1, String filepath2, String filepath3) {
        return base3(filepath1, filepath2, filepath3, 55, 39, 21, 29, 21, 29);
    }


    public String shyf9(String filepath) {
        return base1(filepath, 55, 71);
    }

    public String shyf10(String filepath) {
        return base1(filepath, 65, 91);
    }

    public String shyf11(String filepath) {
        return base1(filepath, 55, 71);
    }

    public String shyf12(String filepath) {
        return base1(filepath, 65, 91);
    }

    public String fengjingruhua1(String filepath) {
        return base1(filepath, 85, 51);
    }

    public String fengjingruhua2(String filepath) {
        return base1(filepath, 41, 64);
    }

    public String[] fengjingruhua3(String filepath1, String filepath2) {
        return base2(filepath1, filepath2, 41, 22, 41, 22);
    }

    public String[] fengjingruhua4(String filepath1, String filepath2) {
        return base2(filepath1, filepath2, 41, 26, 41, 26);

    }

    public String fengjingruhua5(String filepath) {
        return base1(filepath, 1, 1);
    }

    public String[] fengjingruhua(String filepath1, String filepath2) {
        return base2(filepath1, filepath2, 34, 22, 34, 26);
    }

    public String[] fengjingruhua(String filepath1, String filepath2, String filepath3) {
        return base3(filepath1, filepath2, filepath3, 37, 21, 1, 1, 17, 29);
    }

    public String saveBitmap(String bitName, Bitmap mBitmap) {

        File dir = new File(Environment.getExternalStorageDirectory() + "/albumphoto");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File f = new File(Environment.getExternalStorageDirectory() + "/albumphoto/" + bitName);

        try {
            f.createNewFile();
        } catch (IOException e) {
            Toast.makeText(Myapplication.getContext(), "保存错误", Toast.LENGTH_SHORT).show();
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (Environment.getExternalStorageDirectory() + "/albumphoto/" + bitName);
    }


    public Bitmap cropBitmap(Bitmap resource, double width, double height) {
        double w = resource.getWidth();
        double h = resource.getHeight();
        double proportion1 = width / height; //传入比例
        double proportion2 = w / h; //原图比例
        double index;
        double temp, tempx, tempy;
        if (proportion1 <= proportion2) {
            index = h;
            temp = index / height;
            tempx = temp * width;
            return Bitmap.createBitmap(resource, (int) (w - tempx) / 2, 0, (int) tempx, (int) index);

        } else {
            index = w;
            temp = index / width;
            tempy = temp * height;
            return Bitmap.createBitmap(resource, 0, (int) (h - tempy) / 2, (int) index, (int) tempy);
        }


    }

}
