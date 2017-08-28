package com.jason.common.file;

import android.support.annotation.NonNull;

import com.jason.common.IOCloseUtils;
import com.jason.common.StringUtils;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by bob on 2017/7/26.
 *
 * @project JasonCommLibs
 * @email bodroid@163.com
 * @author: <a href="https://github.com/GGKJason">GGKJason</a>
 * @Description
 */

public class FileUtils {
    private static final String TAG = FileUtils.class.getSimpleName();

    private FileUtils() {
        throw new UnsupportedOperationException("Cannot be instantiated");
    }

    public static File getFileByPath(final String path) {
        return StringUtils.isEmptyOptionTrim(path) ? null : new File(path);
    }

    public static boolean isDirectory(final String file) {
        return isDirectory(getFileByPath(file));
    }

    public static boolean isDirectory(final File file) {
        return isExists(file) && file.isDirectory();
    }

    public static boolean isFile(final String file) {
        return isFile(getFileByPath(file));
    }

    public static boolean isFile(final File file) {
        return isExists(file) && file.isFile();
    }

    public static boolean isExists(final String file) {
        return isExists(getFileByPath(file));
    }

    public static boolean isExists(final File file) {
        return file != null && file.exists();
    }

    public static String getFileName(final File file) {
        if (file == null) {
            return null;
        }
        return getFileName(file.getPath());
    }

    public static String getFileName(String file) {
        if (StringUtils.isEmptyOptionTrim(file)) {
            return file;
        }

        int lastSep = file.lastIndexOf(File.separator);
        return lastSep == -1 ? file : file.substring(lastSep + 1);
    }

    public static String getFileNameWithoutExtention(final File file) {
        if (file == null) {
            return null;
        }

        return getFileNameWithoutExtention(file.getPath());
    }

    public static String getFileNameWithoutExtention(final String file) {
        String fileName = getFileName(file);
        if (StringUtils.isEmptyOptionTrim(fileName)) {
            return fileName;
        }

        int lastPoi = fileName.lastIndexOf(".");
        return lastPoi == -1 ? fileName : fileName.substring(0, lastPoi);
    }

    public static boolean rename(final String file, final String name) {
        return rename(getFileByPath(file), name);
    }

    public static boolean rename(final File file, final String name) {
        if (!isExists(file)) {
            return false;
        }

        if (StringUtils.isEmptyOptionTrim(name)) {
            return false;
        }

        if (name.equals(file.getName())) {
            return true;
        }

        File newFile = new File(file.getParent() + File.separator + name);

        return !isExists(newFile) && file.renameTo(newFile);

    }

    public static String getFileLengthFormat(final String file) {
        return getFileLengthFormat(getFileByPath(file));
    }

    public static String getFileLengthFormat(final File file) {
        long length = getFileLength(file);
        return byte2FitMemorySize(length);
    }

    public static long getFileLength(final String file) {
        return getFileLength(getFileByPath(file));
    }

    public static long getFileLength(final File file) {
        if (!isExists(file)) {
            return -1;
        }

        long len = 0;

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    len += getFileLength(f);
                }
            }
        } else {
            len += file.length();
        }

        return len;
    }

    public static long getFileLastModified(final String file) {
        return getFileLastModified(getFileByPath(file));
    }

    public static long getFileLastModified(final File file) {
        if (file == null) {
            return -1;
        }

        return file.lastModified();
    }

    public static boolean createFile(final String file) {
        return createFile(getFileByPath(file));
    }

    public static boolean createFile(final File file) {
        if (file == null) {
            return false;
        }

        if (file.exists()) {
            return file.isFile();
        }

        if (createDirectory(file.getParentFile())) {
            return false;
        }

        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean createDirectory(final String file) {
        return createFile(getFileByPath(file));
    }

    public static boolean createDirectory(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    public static boolean copyFile(final String src, final String dest) {
        return copyFile(getFileByPath(src), getFileByPath(dest));
    }

    public static boolean copyFile(final String src, final File dest) {
        return copyFile(getFileByPath(src), dest);
    }

    public static boolean copyFile(final File src, final String dest) {
        return copyFile(src, getFileByPath(dest));
    }

    public static boolean copyFile(final File src, final File dest) {
        if (!isExists(src) || !createFile(dest)) {
            return false;
        }

        FileChannel srcChannel = null;
        FileChannel destChannel = null;

        try {
            srcChannel = new FileInputStream(src).getChannel();
            destChannel = new FileOutputStream(dest).getChannel();
            srcChannel.transferTo(0, srcChannel.size(), destChannel);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            IOCloseUtils.ioClose(srcChannel);
            IOCloseUtils.ioClose(destChannel);
        }
    }

    public static byte[] readFile2BytesByStream(final String file) {
        return readFile2BytesByMap(getFileByPath(file));
    }

    public static byte[] readFile2BytesByStream(final File file) {
        if (!isExists(file)) {
            return null;
        }

        FileInputStream is = null;
        ByteArrayOutputStream bos = null;
        try {
            is = new FileInputStream(file);
            bos = new ByteArrayOutputStream();
            int bufferSize = 1024 * 8;
            byte[] bytes = new byte[bufferSize];
            int len;
            while ((len = is.read(bytes, 0, bufferSize)) != -1) {
                bos.write(bytes, 0, len);
            }

            return bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            IOCloseUtils.ioClose(is);
            IOCloseUtils.ioClose(bos);
        }
    }

    public static byte[] readFile2BytesByMap(final String file) {
        return readFile2BytesByMap(getFileByPath(file));
    }

    /**
     * 读取不超过2GB的文件，如果文件超过2GB建议通过
     * {mappedBufArray[i] = fileChannel.map(FileChannel.MapMode.READ_ONLY, preLength, regionSize);}
     * 将MappedByteBuffer分块进行读取
     *
     * @param file
     *
     * @return bytes
     *
     * @author: bob
     * create at 2017/8/28 下午3:15
     */
    public static byte[] readFile2BytesByMap(final File file) {
        if (!isExists(file)) {
            return null;
        }
        FileChannel fc = null;

        try {
            fc = new RandomAccessFile(file, "r").getChannel();
            long size = fc.size();
            if (size > Integer.MAX_VALUE) {
                throw new RuntimeException(file.getName() + " is too large");
            }

            int bufferSize = (int) size;
            byte[] result = new byte[bufferSize];
            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, size).load();
            mbb.get(result, 0, bufferSize);

            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            IOCloseUtils.ioClose(fc);
        }

    }

    public static boolean writeFileFromBytesByMap(final String file, final byte[] bytes, final boolean isForce) {
        return writeFileFromBytesByMap(getFileByPath(file), bytes, false, isForce);

    }

    public static boolean writeFileFromBytesByMap(final File file, final byte[] bytes, final boolean isForce) {
        return writeFileFromBytesByMap(file, bytes, false, isForce);

    }

    public static boolean writeFileFromBytesByMap(final String file, final byte[] bytes, final boolean append, final boolean isForce) {
        return writeFileFromBytesByMap(getFileByPath(file), bytes, append, isForce);
    }

    /**
     * 提供bytes写入对应file中
     *
     * InputStream and String
     * {@link #writeFileFromInputStream(File, InputStream, boolean)}
     * {@link #writeFileFromString(File, String, boolean)}
     *
     * @param file
     * @param bytes
     * @param append
     * @param isForce
     *
     * @return write success return true or false
     *
     * @author: bob
     * create at 2017/8/28 下午2:42
     */
    public static boolean writeFileFromBytesByMap(final File file, final byte[] bytes, final boolean append, final boolean isForce) {
        if (bytes == null || !createFile(file)) { // bytes空或者文件不存在并且创建失败
            return false;
        }

        FileChannel fc = null;

        try {
            fc = new FileOutputStream(file, append).getChannel();
            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, fc.size(), bytes.length);
            mbb.put(bytes);

            if (isForce) {
                mbb.force();
            }

            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            IOCloseUtils.ioClose(fc);
        }


    }

    public static boolean writeFileFromInputStream(final File file, final InputStream is, final boolean append) {
        if (is == null || !createFile(file)) {
            return false;
        }

        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file, append));
            int bufferSize = 1024 * 8;
            byte[] bytes = new byte[bufferSize];
            int len;
            while ((len = is.read(bytes, 0, bufferSize)) != -1) {
                os.write(bytes, 0, len);

            }

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            IOCloseUtils.ioClose(os);
            IOCloseUtils.ioClose(is);
        }
    }

    public static boolean writeFileFromString(final File file, final String str, final boolean append) {
        if (file == null || str == null || !createFile(file)) {
            return false;
        }

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, append));
            bw.write(str);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            IOCloseUtils.ioClose(bw);
        }
    }

    public static boolean deleteFile(final String file) {
        return deleteFile(getFileByPath(file), new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        }, true);
    }

    public static boolean deleteFile(final File file) {
        return deleteFile(file, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        }, true);
    }

    public static boolean deleteFile(final String file, final FileFilter fileFilter) {
        return deleteFile(getFileByPath(file), fileFilter, true);
    }

    public static boolean deleteFile(final File file, final FileFilter fileFilter) {
        return deleteFile(file, fileFilter, true);
    }


    public static boolean deleteFile(final File file, final boolean delSelf) {
        return deleteFile(file, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        }, delSelf);
    }

    public static boolean deleteFile(final String file, final FileFilter fileFilter, final boolean delSelf) {
        return deleteFile(getFileByPath(file), fileFilter, delSelf);
    }

    public static boolean deleteFile(final File file, final FileFilter fileFilter, final boolean delSelf) {
        if (!isExists(file)) {
            return false;
        }

        if (isFile(file)) {
            return file.delete();
        }

        if (isDirectory(file)) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (fileFilter.accept(f)) {
                        if (f.isFile()) {
                            return f.delete();
                        } else if (f.isDirectory()) {
                            return deleteFile(f, fileFilter, delSelf);
                        }
                    }
                }
            }

        }

        return !delSelf || file.delete();

    }

    public static List<File> listFileInDirectory(final File directory) {
        return listFileInDirectory(directory, getFileFilter(), false);
    }

    public static List<File> listFileInDirectory(final String directory) {
        return listFileInDirectory(directory, getFileFilter(), false);
    }

    public static List<File> listFileInDirectory(final File directory, final boolean isRecursive) {
        return listFileInDirectory(directory, getFileFilter(), isRecursive);
    }

    public static List<File> listFileInDirectory(final String directory, final boolean isRecursive) {
        return listFileInDirectory(directory, getFileFilter(), isRecursive);
    }

    public static List<File> listFileInDirectory(final String directory, FileFilter fileFilter, final boolean isRecursive) {
        return listFileInDirectory(getFileByPath(directory), fileFilter, isRecursive);
    }

    public static List<File> listFileInDirectory(final File directory, FileFilter fileFilter, final boolean isRecursive) {
        if (!isDirectory(directory)) {
            return null;
        }

        List<File> fileList = null;
        File[] files = directory.listFiles();
        if (files != null) {
            fileList = new ArrayList<>();
            if (null == fileFilter) {
                fileFilter = getFileFilter();
            }

            for (File file : files) {
                if (fileFilter.accept(file)) {
                    fileList.add(file);
                }

                if (isRecursive && isDirectory(file)) {
                    List<File> recursiveList = listFileInDirectory(file, fileFilter, true);
                    if (recursiveList != null) {
                        fileList.addAll(recursiveList);
                    }

                }

            }
        }

        return fileList;
    }

    public static List<File> listFileInDirectory(final String directory, FilenameFilter filenameFilter, final boolean isRecursive) {
        return listFileInDirectory(getFileByPath(directory), filenameFilter, isRecursive);
    }

    public static List<File> listFileInDirectory(final File directory, FilenameFilter filenameFilter, final boolean isRecursive) {
        if (!isDirectory(directory)) {
            return null;
        }

        List<File> fileList = null;
        File[] files = directory.listFiles();
        if (files != null) {
            fileList = new ArrayList<>();
            if (null == filenameFilter) {
                filenameFilter = getFilenameFilter();
            }

            for (File file : files) {
                if (filenameFilter.accept(file, file.getName())) {
                    fileList.add(file);
                }

                if (isRecursive && isDirectory(file)) {
                    List<File> recursiveList = listFileInDirectory(file, filenameFilter, true);
                    if (recursiveList != null) {
                        fileList.addAll(recursiveList);
                    }

                }

            }
        }

        return fileList;
    }

    @NonNull
    private static FilenameFilter getFilenameFilter() {
        FilenameFilter filenameFilter;
        filenameFilter = new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return true;
            }
        };
        return filenameFilter;
    }

    @NonNull
    private static FileFilter getFileFilter() {
        FileFilter fileFilter;
        fileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        };
        return fileFilter;
    }

    /**
     * @param fileSize
     *
     * @return format file size
     *
     * @author: bob
     * create at 2017/8/24 下午5:11
     */
    private static String byte2FitMemorySize(final long fileSize) {
        if (fileSize < 0) {
            return null;
        } else if (fileSize < 1024) {
            return String.format(Locale.getDefault(), "%.3fB", (double) fileSize + 0.0005);

        } else if (fileSize < 1024 * 1024) { //1048576) {
            return String.format(Locale.getDefault(), "%.3fKB", (double) fileSize / 1024 + 0.0005);

        } else if (fileSize < 1024 * 1024 * 1024) { //1073741824){
            return String.format(Locale.getDefault(), "%.3fMB", (double) fileSize / 1048576 + 0.0005);

        } else {
            return String.format(Locale.getDefault(), "%.3fGB", (double) fileSize / 1073741824 + 0.0005);

        }
    }


}
