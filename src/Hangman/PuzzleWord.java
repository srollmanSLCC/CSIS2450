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
    private static ArrayList<String> words = new ArrayList<>();

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
                word = getFallbackWord();
                numberOfLetters = word.length();
            }
        }
        catch (Exception e)
        {
            word = getFallbackWord();
            numberOfLetters = word.length();
        }
    }

    /**
     * A list of words that are used if the CSV is not found.
     *
     * @return random word
     */
    private String getFallbackWord()
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
     * Gets the length of the word
     *
     * @return length of word.
     */
    public int getWordLength()
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

    /**
     * Gets a random word from the JSON array.
     * @return
     */
    private static String getRandomWord()
    {
        try
        {
            if (words.size() > 0)
            {
                Random rand = new Random();
                return words.get(rand.nextInt(words.size()));
            }
        }
        catch(Exception ex)
        {
            //Do nothing.
        }
        return null;
    }

    private static JSONArray getWordsFromApi()
    {
        try
        {
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

                JSONObject resp = new JSONObject(response.toString());
                return resp.getJSONArray("data");
            }
        }
        catch (Exception ex)
        {
            // Do nothing.
        }
        return null;
    }

    /**
     * Gets a list of words from a random word API and stores the list it in memory. 
     * If we have a response already, we will just pull a random word from that.
     * @return A random word to use for a puzzle that exists in the list.
     */
    private static String sendGetWord() throws IOException {
        if (words != null && words.size() > 0)
        {
            return getRandomWord();
        }

        words = sanitizeWordList(getWordsFromApi());
        return getRandomWord();
    }

    private static ArrayList<String> sanitizeWordList(JSONArray wordsFromApi)
    {
        ArrayList<String> allowedWords = new ArrayList<>();
        for (int i = 0; i < wordsFromApi.length(); i++)
        {
            try
            {
                String word = wordsFromApi.get(i).toString();
                if (word.contains("-"))
                {
                    // word is invalid, skip it.
                    continue;
                }
                if (word.contains("'"))
                {
                    // word is invalid, skip it.
                    continue;
                }
                if (word.contains("`"))
                {
                    // word is invalid, skip it.
                    continue;
                }
                if (word.contains("."))
                {
                    // word is invalid, skip it.
                    continue;
                }
                if (word.contains(" "))
                {
                    // word is invalid, skip it.
                    continue;
                }
                allowedWords.add(word);
            }
            catch(Exception ex)
            {
                // Do nothing, move on.
            }

        }

        return allowedWords;
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