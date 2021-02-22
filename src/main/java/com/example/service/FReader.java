package com.example.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;

import java.util.ArrayList;
import java.util.List;
@Service
public class FReader {
    public List<String> getLines(String path){  //path of uploaded file
           String line = "";
           List<String> lines = new ArrayList<>();


        try {
            BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(path));

            while(
                   (line = bufferedReader.readLine().trim())!=null
            ){
                int charCount = line.length();
                if(charCount!=0){
                     System.out.println("["+charCount+ "][BEGIN] "+line);
                       lines.add(line);

                }


            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return lines;
    }


    public  List<List> groupLines(List<String> lines){
       List<List> groups = new ArrayList<>();
     // how to group by 13 lines in one array

   int z=0;
        for(int i=0;i<lines.size();i++){
             List<String> trans = new ArrayList<>();

            if(lines.get(i).substring(0,6).trim().equals("Totals")){
                for(int j=z;j<i;j++){
                    if(!lines.get(j).substring(0,6).trim().equals("Totals")){trans.add(lines.get(j));}

                }
                z=i;


            }


            if(!trans.isEmpty()){   groups.add(trans);}


        }

        System.out.println(groups.size());

        return groups;

    }



    public JSONObject getDataJson(List<String> transaction){


        JSONObject jsonObject = new JSONObject();

        for(int i=0;i<5;i++){

           String[] json = transaction.get(i).split(":");
            String key = json[0].trim().replace(' ','_');
            String val = json[1].trim();
             jsonObject.put((Object)key,val);
//           res+= "\""+json[0].trim().replace(' ','_')+"\":\""+json[1].trim() +"\",";
        }



        String[] keys1 = {};
        String[] keys2 = {};
        List<List> columns = new ArrayList<>();



            int k=5;
            if(transaction.get(k).charAt(0)=='+' && transaction.get(k+3).charAt(0)=='+' ){
                keys1 = transaction.get(k+1).split("!");
                keys2= transaction.get(k+2).split("!");
            }


              List<List> vals = new ArrayList<>();
              for (int i=9;i<transaction.size();i++){
              //Values arrays
              String[] stringOfVals = transaction.get(i).split(" ");
              List<String> valsI= new ArrayList<>();

              for(String val:stringOfVals ){
                  String str = "";
                  if(!val.equals("!") && !val.equals(" ") && !val.equals("-") && !val.isEmpty()){
                      valsI.add(val.replace(" ","_"));
                        }
              }
               vals.add(correctList(valsI));
          }
        System.out.println("[VALS of table ]" + vals.toString()); //2
        List<String> keys = new ArrayList<>();
        if(keys1.length ==keys2.length){
            System.out.println("[KEYS] " + keys1.toString());
             for(int i=0;i<keys1.length;i++){
               if(!keys1[i].isEmpty() && !keys2[i].isEmpty()){keys.add(keys1[i].trim().replace(" ","_")+"_"+keys2[i].trim())  ;}

             }

        }


        JSONArray jsonArray = new JSONArray();
        //NEED TO ADD KEYS+VALUES
        for(List<String> values:vals){
            JSONObject object = new JSONObject();
            int sizeOfKeys = keys.size();
            int sizeOfValues = values.size();
             if(sizeOfKeys ==sizeOfValues ){
                   for(int i =0;i<sizeOfKeys;i++){
                       object.put((Object)keys.get(i),values.get(i));
                   }
                   jsonArray.add(object);
             }



        }



//     for(String key: keys){
//         System.out.println(key);
//         jsonObject.put((Object) key.replace(' ','_'),"EMPTY");
//     }

        jsonObject.put("entries",jsonArray);


        return jsonObject;
    }





    private List<String> correctList(List<String> list){
        // need to separete string elements - in one Strign val
        // and left Doubles

        List<String> res  = new ArrayList<>();
         String str = "";
        for(String val:list){

            try
            {
                double aVal =  Double.parseDouble(val);
//                System.out.println("[Cheking if it is double ] " + aVal);
                res.add(val);
            }
            catch(NumberFormatException e)
            {
                str +=val.trim();
//                System.out.println("[FINALY] " + str);

                //not a double
            }
        }

        res.add(0,str);


        System.out.println(res.toString());
            return res;
    }



    public JSONArray createArrayOfJson(List<List> transactions){
        JSONArray result = new JSONArray();

        for(List<String> transaction: transactions){
            JSONObject object = getDataJson(transaction);
            result.add(object);
        }


        return result;
    }



}
