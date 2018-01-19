package Hangman;

import org.json.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PuzzleWord
{
    private int numberOfLetters;
    private String word;
    private static final String randomWordApiURL = "https://www.randomlists.com/data/words.json";

    public PuzzleWord()
    {
        try
        {
            String tempWord = sendGetWord();
            if (tempWord != null)
            {
                word = tempWord;
                numberOfLetters = word.length();
            }
            else
            {
                word = GetFallbackWord();
                numberOfLetters = word.length();
            }
        }
        catch (Exception e)
        {
            word = GetFallbackWord();
            numberOfLetters = word.length();
        }
    }

    /**
     * A list of words that are used if the CSV is not found.
     *
     * @return random word
     */
    private String GetFallbackWord()
    {
        List<String> fallbackWords = new ArrayList<>();
        fallbackWords.add("squash");
        fallbackWords.add("gallows");
        fallbackWords.add("yggdrasil");
        fallbackWords.add("loquacious");
        fallbackWords.add("pompous");
        fallbackWords.add("derail");
        fallbackWords.add("tesla");
        fallbackWords.add("proven");
        fallbackWords.add("slyly");
        Collections.shuffle(fallbackWords);

        return fallbackWords.get( (int) (Math.random() * (fallbackWords.size())));
    }

    /**
     * Gets the number of letters in the word.
     *
     * @return number of letters in the word
     */
    public int getNumberOfLetters()
    {
        return numberOfLetters;
    }

    /**
     * Gets a randomly selected word.
     *
     * @return the randomly selected word
     */
    public String getWord()
    {
        return word;
    }

    private static String sendGetWord() throws IOException {
        URL obj = new URL(randomWordApiURL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) // success
        {
            BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    con.getInputStream()
                )
            );
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null)
            {
                response.append(inputLine);
            }
            in.close();
            try
            {
                JSONObject resp = new JSONObject(response.toString());
                JSONArray arr = resp.getJSONArray("data");
                if (arr.length() > 0)
                {
                    Random rand = new Random();
                    return arr.get(rand.nextInt(arr.length())).toString();
                }
            }
            catch(Exception ex)
            {
                //Do nothing.
            }

        }
        else
        {
            System.out.println("GET request not worked");
        }
        return null;
    }

    /**
     * toString() method that returns the word.
     */
    @Override
    public String toString()
    {
        return word;
    }

}