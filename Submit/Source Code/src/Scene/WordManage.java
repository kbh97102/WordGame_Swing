package Scene;

import java.io.*;
import java.util.Vector;

public class WordManage {

    private static WordManage wordManageInstance;
    private Vector<String> wordData;
    private static String wordFilePath = "words.txt";

    public WordManage() {
        readFromFile();
        wordFilePath = WordManage.class.getResource("").getPath();
    }
    public static WordManage getInstance(){
        if(wordManageInstance == null){
            wordManageInstance = new WordManage();
        }
        return wordManageInstance;
    }
    public Vector<String> getWordData(){
        return wordData;
    }

    public void print(){

        for(int i=0;i<wordData.size();i++){
            System.out.println(wordData.get(i));
        }
    }
    private void readFromFile() {
        wordData = readFile(wordFilePath);
    }
    public void replaceWordData(Vector<String> newData){
        wordData = newData;
    }

    public void saveWordToFile(){
        saveFile(wordFilePath, wordData);
    }
    private void saveFile(String path, Vector<String> wordData){
        File file = new File(path);

        StringBuilder stringBuilder = new StringBuilder();

        for(String word : wordData){
            stringBuilder.append(word);
            stringBuilder.append("\r\n");
        }
        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
        }
        catch (IOException e1){
            e1.printStackTrace();
        }
    }
    private Vector<String> readFile(String path) {
        Vector<String> wordSet = new Vector<>();

        File file = new File(path);
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while((line = bufferedReader.readLine())!=null){
                wordSet.add(line);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return wordSet;
    }
}
