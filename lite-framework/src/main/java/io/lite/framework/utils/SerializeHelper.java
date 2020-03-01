package io.lite.framework.utils;

import io.lite.framework.common.BizError;
import io.lite.framework.common.ResponseCode;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class SerializeHelper {
    // ------------------------------- serialize ---------------------------
    public static byte[] serialize(Object obj) {

        if (obj == null)
            return null;

        byte[] bytes = null;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
        } catch (IOException e) {
            log.error("serialize_err", e);
            throw new BizError(ResponseCode.SERIALIZE_ERROR);
        }
        return bytes;
    }

    public static <T extends Serializable> T deserialize(byte[] bytes) {
        T obj = null;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            obj = (T) ois.readObject();
        } catch (IOException ex) {
            log.error("serialize_err", ex);
            throw new BizError(ResponseCode.SERIALIZE_ERROR);
        } catch (ClassNotFoundException e) {
            log.error("serialize_err", e);
            throw new BizError(ResponseCode.SERIALIZE_ERROR);
        }
        return obj;
    }


    public static void writeBytesToFile(byte[] bs, String path) throws IOException {
        OutputStream out = new FileOutputStream(path);
        InputStream is = new ByteArrayInputStream(bs);
        byte[] buff = new byte[1024];
        int len = 0;
        while ((len = is.read(buff)) != -1) {
            out.write(buff, 0, len);
        }
        is.close();
        out.close();
    }


    public static byte[] inputToByte(InputStream inputStream) throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int rc;
        while ((rc = inputStream.read(buff, 0, 1024)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }

}
