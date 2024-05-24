package FindingEntity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EntityFinder  {

    private Map<String, Entity> entityMap;
    
    public void initializeEntities() {
        // Đường dẫn tới file JSON
        String filePath = "src/main/java/FileStorge/EntityInfor.json";
        entityMap = readJsonFile(filePath);
    }

    private static Map<String, Entity> readJsonFile(String filePath) {
        Map<String, Entity> entityMap = new HashMap<>();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(filePath)) {
            //chuyển thành JSONArray
            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            for (Object obj : jsonArray) {
                JSONObject entityJson = (JSONObject) obj;
                String fullname = (String) entityJson.get("Fullname");                
                String name = (String) entityJson.get("name");
                String symbol = (String) entityJson.get("symbol");
                String description = (String) entityJson.get("description");
                String image = (String) entityJson.get("image");
                Entity entity = new Entity(fullname ,name, symbol, description, image);
                entityMap.put(name, entity);
                entityMap.put(symbol, entity);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return entityMap;
    }
    
    //loại bỏ tất cả dấu cách và đưa về chữ thường
    private static String preprocessKeyword(String keyword) {
        return keyword.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
    }
    public Entity FindEntity(String text) {
        initializeEntities();                
        String findText = preprocessKeyword(text);
        if (!findText.isEmpty() && entityMap != null) {
            // Duyệt qua tất cả các cặp key-value trong entityMap
            for (Map.Entry<String, Entity> entry : entityMap.entrySet()) {
                // Kiểm tra xem key có null không
                if (entry.getKey() != null && entry.getKey().equals(findText)) {
                    // Nếu tìm thấy, trả về đối tượng Entity tương ứng
                    return entry.getValue();
                }
            }
        }
        // Trả về null nếu không tìm thấy hoặc entityMap là null
        return null;
    }
}