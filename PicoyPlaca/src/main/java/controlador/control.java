/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Leonardo Villarreal
 */
public class control {
    
    //Variable declaration
    static String letters="abcdefghijklmnñopqrstuvwxyz";
    static String numbers="0123456789";
    static String errorSize="";
    static String errorCharacters="";
    static String errorNumber="";
    static String errorType="";
    static String errorDate="";
    static String errorHour="";
    static SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
    static SimpleDateFormat format2 = new SimpleDateFormat("EEEE");
    static SimpleDateFormat format3 = new SimpleDateFormat("HH:mm");
    static String day="";
    public static Date dateSelected = null;

    
    //This function will validate the license plate, if its a car that its length is 6 or 7 characters
    //and that the first 3 characters are letters and the last 3 or 4 are numbers.
    //if its a bike that its length is 6 characters and the first 2 and the last characters are letters
    //and the 3º,4º,5º characters are numbers
    public static boolean validatePlate(String plate, int type){
        switch (type){
            case 1:
                if (plate.length()==6 || plate.length()==7){
                    for(int i=0; i<plate.substring(0, 3).length();i++){
                        if(letters.indexOf(plate.substring(0, 3).charAt(i), 0)==-1) {
                            errorCharacters="You must enter only letters in the first three characters!\n";
                            return false;
			}
                    }
                    for(int j=0; j<plate.substring(3).length(); j++){
                        if(numbers.indexOf(plate.substring(3).charAt(j), 0)==-1) {
                            errorNumber="The last characters must be numbers!\n";
                            return false;
			}
                    }
                    return true;
                }     
                else{
                    errorSize="You must enter a plate number with either 6 or 7 characters!\n";
                    return false;
                }                
            case 2:
                if (plate.length()==6){
                    for(int i=0; i<plate.substring(0, 2).length();i++){
                        if(letters.indexOf(plate.substring(0, 2).charAt(i), 0)==-1) {
                            errorCharacters="You must enter only letters in the first two characters!\n";
                            return false;
			}
                    }
                    for(int j=0; j<plate.substring(2,5).length(); j++){
                        if(numbers.indexOf(plate.substring(2,5).charAt(j), 0)==-1) {
                            errorNumber="The third, fourth and fifth characters must be numbers!\n";
                            return false;
			}
                    }
                    if(letters.indexOf(plate.charAt(5), 0)==-1) {
                        errorCharacters="You must enter only letters in the last character!\n";
                        return false;
                    }
                    return true;
                }else{
                    errorSize="You must enter a plate number with 6 characters\n";
                    return false;
                }               
            default:
                errorType="You must select the vehicle type and enter a plate number\n";
                return false;               
        }       
    }
    
    //this function will validate the date in the format dd-MM-yyyy
    //and will set the string day with the day of the week that the date represents
    public static boolean validateDate(String date){
        try {
            dateSelected = format1.parse(date);
            day = format2.format(dateSelected);
            return true;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            errorDate="You must enter a date in this format (dd-MM-yyyy)!\n";
            return false;
        }
    }
    
    //This function will validate that the hour typed its in the 24H format
    //and that the hours are not higher than 23, and the minutes higher than 59
    public static boolean validateHour(String hour){
        String h=hour.substring(0, 2);
        String m=hour.substring(3);
        int hh,mm;
        
        try{
            hh=Integer.parseInt(h);
            mm=Integer.parseInt(m);
            if(hh>23 && hh<0){
                errorHour="The hour must not exceed 24 and the minutes 59!\n";
                return false;
            }else if(mm>59 && mm<0){
                errorHour="The hour must not exceed 24 and the minutes 59!\n";
                return false;
            }else{
                return true;
            }            
        }catch (Exception e){
            errorHour="You must not type numbers in the hour field!\n"; 
            return false;
        }        
    }
    
    //this function will group all the error strings from the previous methods and return 
    //a complete list of all the errors committed by the user
    public static String totalValidation(String plate, String date, String hour, int type){
        String error="";
        if(!validatePlate(plate, type)){
            error+=errorCharacters + errorSize + errorNumber + errorType;
        }
        if(!validateDate(date)){
            error+=errorDate;
        }
        if(!validateHour(hour)){
            error+=errorHour;
        }
        return error;
    }
    
    
    //This function extracts the last number character form the license plate, wether its a car or a bike
    //and uses the next methods to evaluate if it can be on the road or not.
    //if it can the function will return true, otherwise it will return false.
    public static boolean picoyPlaca(String plate, int type, String hour){
        if(type==1){
            String last=plate.substring(plate.length()-1);
            return lastDigit(last, hour);
        }else if(type==2){
            String last=plate.substring(plate.length()-2,plate.length()-1);
            return lastDigit(last, hour);
        }else{
            return false;
        }
    }
    
    
    
    //this function evaluates the last digit of the license plate, the day of the week
    //and checks if the hour is between the Pico & Placa range.
    //if the last digit corresponds to the respective day and the hour its between the restricted hour
    //it will return false, otherwise will return true.
    public static boolean lastDigit(String last, String hour){
        if(last.equals("1")||last.equals("2")){
            if(day.equals("lunes")){
                if(!hourCheck(hour)){
                    return false;
                }else{
                    return true;
                }
            }else{
                return true;
            }
        }else if(last.equals("3")||last.equals("4")){
            if(day.equals("martes")){
                if(!hourCheck(hour)){
                    return false;
                }else{
                    return true;
                }
            }else{
                return true;
            }
        }else if(last.equals("5")||last.equals("6")){
            if(day.equals("miércoles")){
                if(!hourCheck(hour)){
                    return false;
                }else{
                    return true;
                }
            }else{
                return true;
            }
        }else if(last.equals("7")||last.equals("8")){
            if(day.equals("jueves")){
                if(!hourCheck(hour)){
                    return false;
                }else{
                    return true;
                }
            }else{
                return true;
            }
        }else if(last.equals("9")||last.equals("0")){
            if(day.equals("viernes")){
                if(!hourCheck(hour)){
                    return false;
                }else{
                    return true;
                }
            }else{
                return true;
            }
        }
        return false;       
    }
    
    
    //this function evaluates if the hour typed its between 07:00--09:30
    //or 16:00--19:30 and return false if it is
    public static boolean hourCheck(String hour){
        try{
           Date seven = format3.parse("06:59");
           Date nineThirty = format3.parse("09:30");
           Date sixteen = format3.parse("15:59");
           Date nineteenThirty = format3.parse("19:30");
           Date userHour = format3.parse(hour);
           if(userHour.after(seven) && userHour.before(nineThirty)){
               return false;
           }
           if(userHour.after(sixteen) && userHour.before(nineteenThirty)){
               return false;
           }else{
               return true;
           }
        }catch(Exception e){
            return false;
        }
    }

    //this method returns the day of the week in english.
    public static String getDay() {
        switch (day){
            case "lunes":
                day="Monday";
                break;
            case "martes":
                day="Tuesday";
                break;
            case "miércoles":
                day="Wednesday";
                break;
            case "jueves":
                day="Thursday";
                break;
            case "viernes":
                day="Friday";
                break;
            case "sábado":
                day="Saturday";
                break;
            case "domingo":
                day="Sunday";
                break;
        }
        return day;
    }
    
}
