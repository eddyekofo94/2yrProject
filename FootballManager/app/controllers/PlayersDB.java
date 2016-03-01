package controllers;
import models.*;
import play.*;
import play.mvc.*;
import play.data.*;
import play.data.Form.*;
import play.mvc.Http.*;
import play.mvc.Http.MultipartFormData.FilePart;
import java.io.*;
import java.io.File;
import javax.activation.MimetypesFileTypeMap;
import views.html.*;
import org.im4java.process.ProcessStarter;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

// Import required classes
import java.util.ArrayList;
import java.util.List;

public class PlayersDB extends Controller {
    public Result updatePlayer(Long id){
        Form<Player> playerForm = Form.form(Player.class).fill(Player.find.byId(id));
        
        return ok(updatePlayer.render(id, playerForm));
 
    }
    
    public Result updatePlayerSubmit(Long id){
        
        Form<Player> updatePlayerForm = Form.form(Player.class).bindFromRequest();
        
        //check for errors (based on Player class annotations)
        if(updatePlayerForm.hasErrors()){
            // display the form again
            return badRequest(updatePlayer.render(id, updatePlayerForm));
        }
        Player p = updatePlayerForm.get();
        p.playerID = p.playerID;
        p.update();
        
        // display a success message
        flash("success", "Player "+updatePlayerForm.get().playerName+" has been updated!");
        return redirect("/squad");
    }
}