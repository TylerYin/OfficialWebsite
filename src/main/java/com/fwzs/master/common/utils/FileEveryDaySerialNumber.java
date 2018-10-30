package com.fwzs.master.common.utils;

import java.io.File;
import java.util.Date;
import java.util.List;

public class FileEveryDaySerialNumber extends EveryDaySerialNumber {

	 /**
     * 持久化存储的文件
     */   
    private File file = null;
   
    /**
     * 存储的分隔符
     */
    private final static String FIELD_SEPARATOR = ",";   

    public FileEveryDaySerialNumber(int width, String filename) {
        super(width);
        file = new File(filename);
    }

    @Override
    protected int getOrUpdateNumber(Date current, int start) {
        String date = format(current);
        int num = start;
        if(file.exists()) {
            List<String> list = FileUtils.readList(file);       
            String[] data = list.get(0).split(FIELD_SEPARATOR);
            if(date.equals(data[0])) {
                num = Integer.parseInt(data[1]);
            }
        }
        FileUtils.rewrite(file, date + FIELD_SEPARATOR + (num + 1));
        return num;
    }        
}
