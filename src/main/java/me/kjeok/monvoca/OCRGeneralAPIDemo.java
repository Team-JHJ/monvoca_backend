package me.kjeok.monvoca;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

public class OCRGeneralAPIDemo {

    public static void main(String[] args) {
        String apiURL = "https://2jc1tnj5mc.apigw.ntruss.com/custom/v1/34654/8fbe7ac9010a337ac43efc7b1239b0d54ddf117c69ea650efdd2a1c391cb21e4/general";  // 네이버 CLOVA OCR API URL
        String secretKey = "Ym5UQnVORG5MTlZuYXJvZGtpVnJFYm9NQnFXRWZ4TGI=";
        String imagePath = "src/main/resources/static/voca3.jpg"; // 이미지 경로를 설정합니다.
        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("X-OCR-SECRET", secretKey);

            // 요청을 위한 JSON 객체 생성
            JSONObject json = new JSONObject();
            json.put("version", "V2");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis());

            // 이미지 데이터를 불러와 Base64로 인코딩
            File imageFile = new File(imagePath);
            FileInputStream fis = new FileInputStream(imageFile);
            byte[] imageBytes = new byte[(int) imageFile.length()];
            fis.read(imageBytes);
            fis.close();
            String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);

            // 이미지 데이터를 JSON에 포함
            JSONObject image = new JSONObject();
            image.put("format", "jpg");
            image.put("data", imageBase64); // Base64로 인코딩된 이미지 데이터
            image.put("name", "demo");
            JSONArray images = new JSONArray();
            images.put(image);
            json.put("images", images);

            // JSON을 문자열로 변환
            String postParams = json.toString();

            // 요청 전송
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();

            // 응답 읽기
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            // 전체 OCR 응답 출력
            System.out.println("Full OCR Response: " + response);

            // JSON 응답을 파싱하고 단어만 추출
            JSONObject responseJson = new JSONObject(response.toString());
            List<String> words = new ArrayList<>();
            if (responseJson.has("images")) {
                JSONArray imagesArray = responseJson.getJSONArray("images");
                for (int i = 0; i < imagesArray.length(); i++) {
                    JSONObject imageObject = imagesArray.getJSONObject(i);
                    JSONArray fields = imageObject.getJSONArray("fields");
                    for (int j = 0; j < fields.length(); j++) {
                        JSONObject field = fields.getJSONObject(j);
                        String inferText = field.getString("inferText");

                        // 단어만 리스트에 추가
                        if (isWord(inferText)) {
                            words.add(inferText);
                        }
                    }
                }
            } else {
                System.out.println("'images' 필드를 찾을 수 없습니다.");
            }

            // 단어 리스트 출력
            System.out.println("Extracted Words:");
            for (String word : words) {
                System.out.println(word);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    // 단어인지 확인하는 간단한 메서드 (필요에 따라 수정 가능)
    private static boolean isWord(String text) {
        return text.matches("^[a-zA-Z]+$"); // 영어 단어만 필터링 (영어 알파벳으로만 구성된 경우)
    }
}