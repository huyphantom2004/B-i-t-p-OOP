package Trending;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.util.*;
/**
 *
 * @author Admin
 */
public class TrendExport {
    public String[] mostFrequentTag() {
        String filePath = "src/main/java/FileStorge/Contents.json";
        String[] trending = new String[3];
        try {
            // Read the JSON file
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(filePath));
            // Count the occurrence of each hashtag
            Map<String, Integer> hashtagCounts = new HashMap<>();
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                JSONArray hashtags = (JSONArray) jsonObject.get("Hashtag đi kèm");
                for (Object hashtagObj : hashtags) {
                    String hashtag = (String) hashtagObj;
                    hashtagCounts.put(hashtag, hashtagCounts.getOrDefault(hashtag, 0) + 1);
                }
            }   
            // Sort hashtags by frequency in descending order
            List<Map.Entry<String, Integer>> sortedHashtags = new ArrayList<>(hashtagCounts.entrySet());
            sortedHashtags.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
            int i = 0;
            for (Map.Entry<String, Integer> entry : sortedHashtags) {
                if ((!entry.getKey().equals("")) && (!entry.getKey().equals("null")) && entry.getKey() != null && (!entry.getKey().equals("Không có"))) {
                    trending[i] = entry.getKey();
                    i++;
                    if (i == 3) break;
                }
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trending;
    }
}