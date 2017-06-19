package com.youyou.xiaofeibao;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class FileUtils {
    /**
     * Assets文件复制
     *
     * @param context
     * @param assetsPath assets文件路径，需要带文件名
     * @param dirPath    复制后的文件目录,需要带文件名
     * @throws IOException
     */
    public static void CopyAssetsFile(Context context, String assetsPath, String dirPath) throws IOException {
        File mWorkingPath = new File(dirPath.substring(0, dirPath.lastIndexOf("/")));
        if (!mWorkingPath.exists()) {
            mWorkingPath.mkdirs();
        }

        File outFile = new File(dirPath);
        if (outFile.exists())
            outFile.delete();
        InputStream in = null;
        in = context.getAssets().open(assetsPath);
        OutputStream out = new FileOutputStream(outFile);
        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
    }

    /**
     * 文件夹复制<br/>
     * 耗时太多，不建议使用，最好先使用zip文件进行复制，然后解压缩
     *
     * @param assetDir
     * @param dir
     * @throws IOException
     */
    private void CopyAssetsFiles(Context context, String assetDir, String dir) throws IOException {
//        Log.e("main","assetDir:"+assetDir+"    dir:"+dir);
        String[] files;
        try {
            files = context.getResources().getAssets().list(assetDir);
        } catch (IOException e1) {
            return;
        }
        File mWorkingPath = new File(dir);
        //if this directory does not exists, make one.
        if (!mWorkingPath.exists()) {
            if (!mWorkingPath.mkdirs()) {

            }
        }
        for (int i = 0; i < files.length; i++) {
            String fileName = files[i];
            //we make sure file name not contains '.' to be a folder.
            if (!fileName.contains(".")) {
                if (0 == assetDir.length()) {
                    CopyAssetsFiles(context, fileName, dir + fileName + "/");
                } else {
                    CopyAssetsFiles(context, assetDir + "/" + fileName, dir + fileName + "/");
                }
                continue;
            }
            File outFile = new File(mWorkingPath, fileName);
            if (outFile.exists())
                outFile.delete();
            InputStream in = null;
            if (0 != assetDir.length())
                in = context.getAssets().open(assetDir + "/" + fileName);
            else
                in = context.getAssets().open(fileName);
            OutputStream out = new FileOutputStream(outFile);
            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        }
    }


    /**
     * ------------------------------------------------------------------------
     * -------- ZIP压缩（解压缩工具方法）
     * --------------------------------------------------
     * -------------------------------
     */
    private static final int BUFF_SIZE = 1024 * 1024; // 1M Byte

    private static void zipFile(File resFile, ZipOutputStream zipout,
                                String rootpath) throws IOException {
        rootpath = rootpath
                + (rootpath.trim().length() == 0 ? "" : File.separator)
                + resFile.getName();
        if (resFile.isDirectory()) {
            File[] fileList = resFile.listFiles();
            for (File file : fileList) {
                zipFile(file, zipout, rootpath);
            }
        } else {
            byte buffer[] = new byte[BUFF_SIZE];
            BufferedInputStream in = new BufferedInputStream(
                    new FileInputStream(resFile), BUFF_SIZE);
            zipout.putNextEntry(new ZipEntry(rootpath));
            int realLength;
            while ((realLength = in.read(buffer)) != -1) {
                zipout.write(buffer, 0, realLength);
            }
            in.close();
            zipout.flush();
            zipout.closeEntry();
        }
    }

    /**
     * 解压缩含有文件夹的压缩文件
     *
     * @param zipFile          需要解压缩的ZIP文件
     * @param folderPath       解压缩的文件目录
     * @param isUseZipFileName 是否带zip文件名解压
     * @throws java.util.zip.ZipException
     * @throws java.io.IOException
     */
    public static void upZipFile(File zipFile, String folderPath, boolean isUseZipFileName) throws
            IOException {
        /*if (Log.isLoggable(LogTag.TEMP, Log.DEBUG)) {
                Log.d(LogTag.TEMP, "  zipFileName:"+zipFile.getAbsolutePath()+"  folderPath:"+folderPath);
        	}*/
        String zipFileFloderName = zipFile.getName().replace(".zip", "");
        File desDir = new File(folderPath);
        if (!desDir.exists()) {
            // 创建目标目录
            desDir.mkdirs();
        }

        ZipFile zf = new ZipFile(zipFile);
        for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements(); ) {
            ZipEntry entry = ((ZipEntry) entries.nextElement());
            String entryName = entry.getName();
            if (isUseZipFileName) {
                entryName = zipFileFloderName + File.separator + entryName;
            }
            if (entry.isDirectory()) {
                String tmpStr = folderPath + File.separator + entryName;
                //tmpStr = new String(tmpStr.getBytes("UTF-8"), "UTF-8");
                File folder = new File(tmpStr);
                folder.mkdirs();
            } else {
                InputStream is = zf.getInputStream(entry);
                String str = folderPath + File.separator + entryName;
                //str = new String(str.getBytes("UTF-8"), "GBKD");  TODO 转换编码，避免中文时乱码

                File subFile = new File(str.substring(0, str.lastIndexOf("/")));//判断当前文件的父路径是否存在
                if (!subFile.exists()) {
                    subFile.mkdirs();
                }
                File desFile = new File(str);
                if (!desFile.exists()) {
                    // 创建目标文件

                    desFile.createNewFile();
                }
                OutputStream os = new FileOutputStream(desFile);
                byte[] buffer = new byte[1024];
                int realLength;
                while ((realLength = is.read(buffer)) > 0) {
                    os.write(buffer, 0, realLength);
                    os.flush();
                }
                is.close();
                os.close();
            }


        }
        zf.close();

    }

    /**
     * 复制文件或文件夹
     *
     * @param srcPath
     * @param destDir 目标文件所在的目录
     * @return
     */
    public static boolean copyGeneralFile(String srcPath, String destDir) {
        boolean flag = false;
        File file = new File(srcPath);
        if (!file.exists()) {
            System.out.println("源文件或源文件夹不存在!");
            return false;
        }
        if (file.isFile()) { // 源文件  
            System.out.println("下面进行文件复制!");
            flag = copyFile(srcPath, destDir);
        } else if (file.isDirectory()) {
            System.out.println("下面进行文件夹复制!");
            flag = copyDirectory(srcPath, destDir);
        }

        return flag;
    }

    /**
     * 复制文件
     *
     * @param srcPath 源文件绝对路径
     * @param destDir 目标文件所在目录
     * @return boolean
     */
    private static boolean copyFile(String srcPath, String destDir) {
        boolean flag = false;

        File srcFile = new File(srcPath);
        if (!srcFile.exists()) { // 源文件不存在  
            System.out.println("源文件不存在");
            return false;
        }
        // 获取待复制文件的文件名  
        String fileName = srcPath
                .substring(srcPath.lastIndexOf(File.separator));
        String destPath = destDir + fileName;
        if (destPath.equals(srcPath)) { // 源文件路径和目标文件路径重复  
            System.out.println("源文件路径和目标文件路径重复!");
            return false;
        }
        File destFile = new File(destPath);
        if (destFile.exists() && destFile.isFile()) { // 该路径下已经有一个同名文件  
            System.out.println("目标目录下已有同名文件!");
            return false;
        }

        File destFileDir = new File(destDir);
        destFileDir.mkdirs();
        try {
            FileInputStream fis = new FileInputStream(srcPath);
            FileOutputStream fos = new FileOutputStream(destFile);
            byte[] buf = new byte[1024];
            int c;
            while ((c = fis.read(buf)) != -1) {
                fos.write(buf, 0, c);
            }
            fis.close();
            fos.close();

            flag = true;
        } catch (IOException e) {
            //  
        }

        if (flag) {
            System.out.println("复制文件成功!");
        }

        return flag;
    }

    /**
     * @param srcPath 源文件夹路径
     * @param destDir 目标文件夹所在目录
     * @return
     */
    private static boolean copyDirectory(String srcPath, String destDir) {
        System.out.println("复制文件夹开始!");
        boolean flag = false;

        File srcFile = new File(srcPath);
        if (!srcFile.exists()) { // 源文件夹不存在  
            System.out.println("源文件夹不存在");
            return false;
        }
        // 获得待复制的文件夹的名字，比如待复制的文件夹为"E://dir"则获取的名字为"dir"  
        String dirName = getDirName(srcPath);
        // 目标文件夹的完整路径  
        String destPath = destDir + File.separator + dirName;
        // System.out.println("目标文件夹的完整路径为：" + destPath);  

        if (destPath.equals(srcPath)) {
            System.out.println("目标文件夹与源文件夹重复");
            return false;
        }
        File destDirFile = new File(destPath);
        if (destDirFile.exists()) { // 目标位置有一个同名文件夹  
            System.out.println("目标位置已有同名文件夹!");
            return false;
        }
        destDirFile.mkdirs(); // 生成目录  

        File[] fileList = srcFile.listFiles(); // 获取源文件夹下的子文件和子文件夹
        if (fileList.length == 0) { // 如果源文件夹为空目录则直接设置flag为true，这一步非常隐蔽，debug了很久  
            flag = true;
        } else {
            for (File temp : fileList) {
                if (temp.isFile()) { // 文件  
                    flag = copyFile(temp.getAbsolutePath(), destPath);
                } else if (temp.isDirectory()) { // 文件夹  
                    flag = copyDirectory(temp.getAbsolutePath(), destPath);
                }
                if (!flag) {
                    break;
                }
            }
        }

        if (flag) {
            System.out.println("复制文件夹成功!");
        }

        return flag;
    }

    /**
     * 获取待复制文件夹的文件夹名
     *
     * @param dir
     * @return String
     */
    private static String getDirName(String dir) {
        if (dir.endsWith(File.separator)) { // 如果文件夹路径以"//"结尾，则先去除末尾的"//"
            dir = dir.substring(0, dir.lastIndexOf(File.separator));
        }
        return dir.substring(dir.lastIndexOf(File.separator) + 1);
    }

    /**
     * 删除文件或文件夹
     *
     * @param path 待删除的文件的绝对路径
     * @return boolean
     */
    public static boolean deleteGeneralFile(String path) {
        boolean flag = false;

        File file = new File(path);
        if (!file.exists()) { // 文件不存在  
            // 日志
            /*if (Log.isLoggable(LogTag.OTA, Log.DEBUG)) {
                Log.d(LogTag.OTA, " 要删除的文件不存在！-->> ");
			}*/
        }

        if (file.isDirectory()) { // 如果是目录，则单独处理  
            flag = deleteDirectory(file.getAbsolutePath());
        } else if (file.isFile()) {
            flag = deleteFile(file);
        }

        if (flag) {
            // 日志

        }

        return flag;
    }

    /**
     * 删除文件
     *
     * @param file
     * @return boolean
     */
    public static boolean deleteFile(File file) {
        if (null != file) {
            return file.delete();
        }
        return false;
    }

    /**
     * 删除目录及其下面的所有子文件和子文件夹，注意一个目录下如果还有其他文件或文件夹
     * 则直接调用delete方法是不行的，必须待其子文件和子文件夹完全删除了才能够调用delete
     *
     * @param path path为该目录的路径
     */
    private static boolean deleteDirectory(String path) {
        boolean flag = true;
        File dirFile = new File(path);
        if (!dirFile.isDirectory()) {
            return flag;
        }
        File[] files = dirFile.listFiles();
        for (File file : files) { // 删除该文件夹下的文件和文件夹
            // Delete file.  
            if (file.isFile()) {
                flag = deleteFile(file);
            } else if (file.isDirectory()) {// Delete folder  
                flag = deleteDirectory(file.getAbsolutePath());
            }
            if (!flag) { // 只要有一个失败就立刻不再继续  
                break;
            }
        }
        flag = dirFile.delete(); // 删除空目录  
        return flag;
    }

    /**
     * 由上面方法延伸出剪切方法：复制+删除
     *
     * @param destDir 同上
     */
    public static boolean cutGeneralFile(String srcPath, String destDir) {
        if (!copyGeneralFile(srcPath, destDir)) {
            System.out.println("复制失败导致剪切失败!");
            return false;
        }
        if (!deleteGeneralFile(srcPath)) {
            System.out.println("删除源文件(文件夹)失败导致剪切失败!");
            return false;
        }

        System.out.println("剪切成功!");
        return true;
    }


}
