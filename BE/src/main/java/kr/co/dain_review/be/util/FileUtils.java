package kr.co.dain_review.be.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.UUID;

import static kr.co.dain_review.be.config.WebMVCConfig.filePath;

public class FileUtils {
    public static void createDirectoryIfNotExists(String filePath) {
        // filePath에서 파일이 위치할 폴더 경로만 추출
        File file = new File(filePath);
        File directory = file.getParentFile();

        if (directory != null && !directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("디렉토리가 성공적으로 생성되었습니다: " + directory.getAbsolutePath());
            } else {
                System.out.println("디렉토리 생성 실패: " + directory.getAbsolutePath());
            }
        }
    }

    public static void saveFile(MultipartFile mfile, String fileName) {
        String file = filePath + fileName;
        createDirectoryIfNotExists(file);

        try (InputStream inputStream = mfile.getInputStream();
             OutputStream outputStream = new FileOutputStream(file)){
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public static void saveFile(byte[] mfile, String fileName) {
        String file = filePath + fileName;

        createDirectoryIfNotExists(file);

        try (OutputStream outputStream = new FileOutputStream(file)){
            outputStream.write(mfile);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }


    public static String getExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName == null || !fileName.contains(".")) {
            return ""; // 확장자가 없는 경우 빈 문자열 반환
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static String setNewName(MultipartFile file){
        String name = String.valueOf(UUID.randomUUID());
        String fileName = file.getOriginalFilename();
        if (fileName == null || !fileName.contains(".")) {
            return name; // 확장자가 없는 경우 빈 문자열 반환
        }
        return name +"."+ fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static String setNewName(String  fileName){
        String name = String.valueOf(UUID.randomUUID());
        if (fileName == null || !fileName.contains(".")) {
            return name; // 확장자가 없는 경우 빈 문자열 반환
        }
        return name +"."+ fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static void deleteFile(String fileName) {
        File file = new File(filePath+fileName);
        if (file.exists()) { // 파일이 실제로 존재하는지 확인
            file.delete(); // 파일 삭제 시도 후 결과 반환
        } else {
            System.out.println("파일이 존재하지 않습니다: " + filePath);
        }
    }


    public static void deleteFolder(String path) {
        File folder = new File(path);
        try {
            if(folder.exists()){
                File[] folder_list = folder.listFiles(); //파일리스트 얻어오기

                for (int i = 0; i < folder_list.length; i++) {
                    if(folder_list[i].isFile()) {
                        folder_list[i].delete();
                    }else {
                        FileUtils.deleteFolder(folder_list[i].getPath()); //재귀함수호출
                    }
                    folder_list[i].delete();
                }
                folder.delete(); //폴더 삭제
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
