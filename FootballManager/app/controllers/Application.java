package controllers;
import java.util.*;
import play.mvc.Controller;
import play.mvc.Result;
import play.*;
//fixture upload imports
import play.mvc.Http.*;
import play.mvc.Http.MultipartFormData.FilePart;
import java.io.*;
import java.io.File;
import javax.activation.MimetypesFileTypeMap;

import play.data.*;
import play.data.Form.*;

import views.html.*;
import models.*;

 public class Application extends Controller {

      public Result index() {

        return ok(index.render());
    }

    public Result fixtures() {
List<Fixtures> fixture = Fixtures.findAll();
        return ok(fixtures.render(fixture));
    }

    public Result leagueTable() {

        return ok(leagueTable.render());
    }

    public Result playerDB(Long position) {
        
        List<Position> positions = Position.find.where().orderBy("position asc").findList();
        // get the list of team attributes
        List<Player> players = new ArrayList<Player>();
        if(position == 0){
            players = Player.findAll();
        }
        else{
            for(int i = 0; i< positions.size();i++){
                if(positions.get(i).id == position){
                    players = positions.get(i).players;
                    break;
                }
            }
        }
        return ok(playerDB.render(positions, players));
    }
    
    public Result squad(Long position) {
        
        List<Position> positions = Position.find.where().orderBy("position asc").findList();
        // get the list of team attributes
        List<Player> players = new ArrayList<Player>();
        if(position == 0){
            players = Player.findAll();
        }
        else{
            for(int i = 0; i< positions.size();i++){
                if(positions.get(i).id == position){
                    players = positions.get(i).players;
                    break;
                }
            }
        }
        return ok(playerDB.render(positions, players));
    }
    
    public Result login() {

        return ok(login.render());
    }
    
    public Result register() {

        return ok(register.render());
    }
    
    public Result addPlayer(){
        Form<Player> addPlayerForm = Form.form(Player.class);
        return ok(addPlayer.render(addPlayerForm));
    }
    public Result addPlayerSubmit(){
        Form<Player> newPlayerForm = Form.form(Player.class).bindFromRequest();
        
        if(newPlayerForm.hasErrors()){
            flash("sadf");
            return badRequest(addPlayer.render(newPlayerForm));
            
        }
        newPlayerForm.get().save();
        flash("Success", "Player "+ newPlayerForm.get().playerName+" has been created");
        
        return redirect("/");
    }
    
    
    //fixtures upload
    public static void uploadFixtures(){
//get file data 
MultipartFormData data = request().body().asMultipartFormData();
FilePart uploaded = data.getFile("upload");
String fileResult = saveFile(uploaded);
flash("success","Fixtures has been created"+fileResult);
}

//save file data
public static String saveFile(FilePart uploaded){
if(uploaded !=null){
String fileName = "fixtures";
String extension ="txt";
String mimeType = uploaded.getContentType();
if(mimeType.startsWith("text/")){
//create file from data
File file = uploaded.getFile();
//save as fixtures.txt
file.renameTo(new File("public/fixtures/",fileName + "."+ extension));
return "/file uploaded";

}
}
return "no file";
}

}
