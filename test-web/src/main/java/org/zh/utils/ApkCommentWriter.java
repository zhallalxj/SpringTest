package org.zh.utils;

import org.apache.log4j.Logger;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

/**
 * apk 快速分包
 * 调用 ApkCommentWriter.writeComment(srcApkPath,destPath,)
 *
 * @author
 */
public final class ApkCommentWriter {

    private static final Logger log = Logger.getLogger(ApkCommentWriter.class);


    public static void main(String[] args) {

        String str = "C:/Users/ZhaoHang/Desktop/test.apk";

        String tmpDir = "C:/Users/ZhaoHang/Desktop";

        File file = writeComment(str,tmpDir,"1");


    }

    public static File writeComment(String apkFilePath, String destPath, String guildId) {

        File apkFile = new File(apkFilePath);
        File outputDir = new File(destPath);

        return writeComment(apkFile, outputDir, guildId);
    }

    /**
     * @param apkFile  原始包文件
     * @param destPath 输出包位置
     * @param guildId  工会id
     * @return
     */
    public static File writeComment(File apkFile, File destPath, String guildId) {

        if (apkFile == null || !apkFile.exists()) {
            log.error("源apk文件不存在！！srcApkPath:" + apkFile);
            return null;
        }
        if (destPath == null || !destPath.exists()) {
            destPath.mkdirs();
        }

        if (guildId == null || guildId.isEmpty()) {
            log.error("要写入的guildId不合法！！guildId:" + guildId);
            return null;
        }
        final String baseName = Helper.getBaseName(apkFile.getName());
        final String extName = Helper.getExtension(apkFile.getName());

        try {
            final String apkName = baseName + "-" + guildId + "." + extName;
            File destFile = new File(destPath, apkName);
            Helper.copyFile(apkFile, destFile);
            Helper.writeMarket(destFile, guildId);
            if (Helper.verifyMarket(destFile, guildId)) {
                log.info("写入guildId成功！！guildId:" + guildId + ", srcApk:" + apkFile + ",destApk:" + destFile.getAbsolutePath());
                return destFile;
            } else {
                destFile.delete();
                log.info("写入guildId失败！！guildId:" + guildId + ", srcApk:" + apkFile + ",destApk:" + destFile.getAbsolutePath());
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }


    public static class Helper {

        static final String UTF_8 = "UTF-8";
        static final int ZIP_COMMENT_MAX_LENGTH = 65535;
        static final int SHORT_LENGTH = 2;
        static final byte[] MAGIC = new byte[]{0x21, 0x5a, 0x58, 0x4b, 0x21}; //!ZXK!

        private static boolean isMagicMatched(byte[] buffer) {
            if (buffer.length != MAGIC.length) {
                return false;
            }
            for (int i = 0; i < MAGIC.length; ++i) {
                if (buffer[i] != MAGIC[i]) {
                    return false;
                }
            }
            return true;
        }

        private static void writeBytes(byte[] data, DataOutput out) throws IOException {
            out.write(data);
        }

        private static void writeShort(int i, DataOutput out) throws IOException {
            ByteBuffer bb = ByteBuffer.allocate(SHORT_LENGTH).order(ByteOrder.LITTLE_ENDIAN);
            bb.putShort((short) i);
            out.write(bb.array());
        }

        private static short readShort(DataInput input) throws IOException {
            byte[] buf = new byte[SHORT_LENGTH];
            input.readFully(buf);
            ByteBuffer bb = ByteBuffer.wrap(buf).order(ByteOrder.LITTLE_ENDIAN);
            return bb.getShort(0);
        }


        /**
         * @param file   原始文件
         * @param market 渠道号
         * @Description: 修改apk的渠道号
         */
        public static void writeMarket(final File file, final String market) throws IOException {
            writeZipComment(file, market);
        }

        /**
         * 读取apk的渠道号
         *
         * @param file apk文件
         * @return 渠道号
         */
        public static String readMarket(final File file) throws Exception {
            return readZipComment(file);
        }

        public static void writeZipComment(File file, String comment) throws IOException {
            if (hasZipCommentMagic(file)) {
                log.warn("Zip comment already exists, ignore.");
                return;
            }
            // {@see java.util.zip.ZipOutputStream.writeEND}
            byte[] data = comment.getBytes(UTF_8);
            final RandomAccessFile raf = new RandomAccessFile(file, "rw");
            raf.seek(file.length() - SHORT_LENGTH);
            // write zip comment length
            // (content field length + length field length + magic field length)
            writeShort(data.length + SHORT_LENGTH + MAGIC.length, raf);
            // write content
            writeBytes(data, raf);
            // write content length
            writeShort(data.length, raf);
            // write magic bytes
            writeBytes(MAGIC, raf);
            raf.close();
        }

        public static boolean hasZipCommentMagic(File file) throws IOException {
            RandomAccessFile raf = null;
            try {
                raf = new RandomAccessFile(file, "r");
                long index = raf.length();
                byte[] buffer = new byte[MAGIC.length];
                index -= MAGIC.length;
                // read magic bytes
                raf.seek(index);
                raf.readFully(buffer);
                // check magic bytes matched
                return isMagicMatched(buffer);
            } finally {
                if (raf != null) {
                    raf.close();
                }
            }
        }

        public static String readZipComment(File file) throws Exception {
            RandomAccessFile raf = null;
            try {
                raf = new RandomAccessFile(file, "r");
                long index = raf.length();
                byte[] buffer = new byte[MAGIC.length];
                index -= MAGIC.length;
                // read magic bytes
                raf.seek(index);
                raf.readFully(buffer);
                // if magic bytes matched
                if (isMagicMatched(buffer)) {
                    index -= SHORT_LENGTH;
                    raf.seek(index);
                    // read content length field
                    int length = readShort(raf);
                    if (length > 0) {
                        index -= length;
                        raf.seek(index);
                        // read content bytes
                        byte[] bytesComment = new byte[length];
                        raf.readFully(bytesComment);
                        return new String(bytesComment, UTF_8);
                    } else {
                        throw new Exception("Zip comment content not found");
                    }
                } else {
                    throw new Exception("Zip comment magic bytes not found");
                }
            } finally {
                if (raf != null) {
                    raf.close();
                }
            }
        }


        public static boolean verifyMarket(final File file, final String market) throws Exception {
            return market.equals(readMarket(file));
        }

        public static void copyFile(File src, File dest) throws IOException {
            if (!dest.exists()) {
                dest.createNewFile();
            }
            FileChannel source = null;
            FileChannel destination = null;
            try {
                source = new FileInputStream(src).getChannel();
                destination = new FileOutputStream(dest).getChannel();
                destination.transferFrom(source, 0, source.size());
            } finally {
                if (source != null) {
                    source.close();
                }
                if (destination != null) {
                    destination.close();
                }
            }
        }

        public static boolean deleteDir(File dir) {
            File[] files = dir.listFiles();
            if (files == null || files.length == 0) {
                return false;
            }
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDir(file);
                } else {
                    file.delete();
                }
            }
            return true;
        }


        public static String getExtension(final String fileName) {
            int dot = fileName.lastIndexOf(".");
            if (dot > 0) {
                return fileName.substring(dot + 1);
            } else {
                return null;
            }
        }

        public static String getBaseName(final String fileName) {
            int dot = fileName.lastIndexOf(".");
            if (dot > 0) {
                return fileName.substring(0, dot);
            } else {
                return fileName;
            }
        }
    }

}