/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.sort;

public class AnagramDictionary {

    HashMap<Integer,ArrayList<String>> hmLength=new HashMap<Integer,ArrayList<String>>();
    HashSet<String> hs= new HashSet<String>();
    HashMap<String,ArrayList<String>> hm= new HashMap<String,ArrayList<String>>();
    ArrayList<String> wordList= new ArrayList<String>();
    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private int wordLength=DEFAULT_WORD_LENGTH;


    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;

        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            String temp=sortLetters(word);
            hs.add(word);

            if(hm.containsKey(temp)) {
                hm.get(temp).add(word);
            }
            else{
                hm.put(temp,new ArrayList<String>());
                hm.get(temp).add(word);
            }
            int size=word.length();
            if(hmLength.containsKey(size)){
                hmLength.get(size).add(word);
            }
            else{
                hmLength.put(size,new ArrayList<String>());
                hmLength.get(size).add(word);
            }
        }
    }

    public boolean isGoodWord(String word, String base){
        if(hs.contains(word) && !word.contains(base))
        return true;
        else return false;
    }

    public String sortLetters(String word){
        char [] letters=word.toCharArray();
        Arrays.sort(letters);
        return new String(letters);
    }



    public List<String> getAnagrams(String targetWord) {
        //Iterator it = wordList.iterator();
        ArrayList<String> result = new ArrayList<String>();
        for(String s:wordList) {
            if ((s.length() == targetWord.length()) && (sortLetters(targetWord).equals(sortLetters(s)))){

            result.add(s);

            }
            String t = sortLetters(targetWord);
            if(hm.containsKey(t)){
                hm.get(t).add(targetWord);
            }else{
                hm.put(t,new ArrayList<String>());
                hm.get(t).add(targetWord);
            }

        }

        /*while (it.hasNext()) {
            String w = new String();
            String unsortedw = new String();
            String unsortedt = new String();
            w = (String) it.next();
            unsortedw = (String) it.next();
            unsortedt = targetWord;
            if (w.length() == targetWord.length()) {
                Arrays.sort(targetWord.toCharArray());
                Arrays.sort(w.toCharArray());
                if (targetWord.equals(w)){
                    result.add(unsortedw);
                }

            }

        }*/
        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        char i='a';
        while(i<='z') {
            String x = word + i;
            String sortx = sortLetters(x);
            if (hm.containsKey(sortx))
                for (String s : hm.get(sortx)){
                    if(isGoodWord(s,word))
                    result.add(s);}
            i++;
        }

            return result;

    }

    public String pickGoodStarterWord() {
            ArrayList<String> list=hmLength.get(wordLength);
            int i=random.nextInt(list.size());
        //can write in this way also.
        while(!(getAnagramsWithOneMoreLetter(list.get(i)).size()>=MIN_NUM_ANAGRAMS)){
        i=(i+1)%list.size();
        }
        if(wordLength<MAX_WORD_LENGTH){
            wordLength+=1;
        }
        //if(getAnagramsWithOneMoreLetter(list.get(i)).size()<MIN_NUM_ANAGRAMS)
         //       pickGoodStarterWord();
            return list.get(i);

    }
}
