package core;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import javafx.stage.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.Arrays;
import java.util.Random;

public class StartGame extends Application {
    Stage primaryStage;
    Scene mainMenu, singlePlayerMenu, playerOneStage,playerTwoStage, victoryMenu, themeMenu;
    public Background theme;
    int firstMove=0;
    public boolean easyBot,mediumBot,hardBot,bot,gameStart,target;
    hardBotClass hb=new hardBotClass();
    // Create and initialize cell
    public Cell[][] cell = new Cell[6][7];
    public Cell[][] cellTwo = new Cell[6][7];
    public Cell targetSpot,mostRecent,avoid=cell[0][0];
    GridPane playerOne, playerTwo;
    //This label will alternate everytime sendTurn is played
    //This char will alternate everytime sendTurn is played
    public char currentPlayer='a';
    public char whoseTurn='X';
    public Label victoryLabel;


    public class Cell extends Pane
    {

        /**
         * token is the current player's token
         * ava represents if the location is available to play on
         * column is the column location of this board piece
         * row is the row location of this board piece
         * top determines if this piece is a top piece
         */
        private char token = ' ';
        private boolean ava;
        private int column;
        private boolean top=false;
        private int row;


        /**
         * Constructor
         */

    public Cell()
    {
        setStyle("-fx-border-color: black");
        this.setPrefSize(2000, 2000);
        this.setOnMouseClicked(e -> handleMouseClick());

    }
    /**
     * Sets the row
     */
    public void setRow(int n){
        row=n;

    }

    public char getCurrentPlayer(){
            return currentPlayer;
        }

    public void receiveTurn(){



    }

    /**
     * Sets the board piece above the current board piece to available
     */
    public void setNext(){
        if(this.isTop()==true){
            this.ava=false;
            return;
        }
        else{
            this.ava=false;
            if(currentPlayer=='a') cell[this.row-1][this.column].setAvailable(true);
            else if(currentPlayer=='b') cellTwo[this.row-1][this.column].setAvailable(true);}
    }
    /**
     * @return  row
     */
    public int getRow(){
        return row;
    }
    /**
     * Set column to @param
     */
    public void setColumn(int n){
        column=n;

    }
    /**
     * @return  Column
     */
    public int getColumn(){
        return column;
    }
    /**
     * set ava to @param
     */
    public void setAvailable(boolean c){
        ava=c;

    }
    /**
     * @return ava
     */
    public boolean isAvailable(){
        return ava;
    }

    /** Return token */
    public char getToken() {
        return token;
    }
    /**
     * sets this area to top
     */
    public void setTop(){
        top=true;

    }

    /**
     * @return  top
     */
    public boolean isTop(){
        return top;
    }
    /**
     * @return  available cell
     */

    public Cell[][] getCellTwo(){
        return cellTwo;
    }


    /** Set a new token */
    public void setToken(char c, Cell cell)
    {
        
        mostRecent=cell;
        token = c;



        if (token == 'X')
        {
            Ellipse ellipse = new Ellipse(cell.getWidth() / 2,
            cell.getHeight() / 2, cell.getWidth() / 2 - 10,
            cell.getHeight() / 2 - 10);
            ellipse.centerXProperty().bind(cell.widthProperty().divide(2));
            ellipse.centerYProperty().bind(cell.heightProperty().divide(2));
            ellipse.radiusXProperty().bind(cell.widthProperty().divide(2).subtract(10));
            ellipse.radiusYProperty().bind(cell.heightProperty().divide(2).subtract(10));
            ellipse.setStroke(Color.BLACK);
            ellipse.setFill(Color.RED);
            cell.setNext();
            cell.token=c;
            cell.getChildren().add(ellipse); // Add the ellipse to the pane
            
            if(currentPlayer=='a') {sendTurn();}
            
        }
        
        else if (token == 'O') {
            Ellipse ellipse = new Ellipse(cell.getWidth() / 2,
                    cell.getHeight() / 2, cell.getWidth() / 2 - 10,
                    cell.getHeight() / 2 - 10);
            ellipse.centerXProperty().bind(cell.widthProperty().divide(2));
            ellipse.centerYProperty().bind(cell.heightProperty().divide(2));
            ellipse.radiusXProperty().bind(cell.widthProperty().divide(2).subtract(10));
            ellipse.radiusYProperty().bind(cell.heightProperty().divide(2).subtract(10));
            ellipse.setStroke(Color.BLACK);
            ellipse.setFill(Color.BLACK);
            cell.token=c;
            cell.setNext();
            getChildren().add(ellipse); // Add the ellipse to the pane
            
            if(currentPlayer=='b') {sendTurn();}
            
        }

    }



    /** Handle a mouse click event */
    public void handleMouseClick()
    {   
        //Initiates mostRecent on the first move only
        if(firstMove==0){firstMove++;
        mostRecent=this;
        };

      
        if(bot==true && currentPlayer=='b'){
            return;

        }
        // If cell is empty and game is not over

        else if(gameStart==true && findAvailable(this.row,this.column)==true )
        {

            if(currentPlayer=='a'&& bot==false){
                mostRecent.setToken(whoseTurn,mostRecent);
                cellTwo[mostRecent.row][mostRecent.column].setToken('X',cellTwo[mostRecent.row][mostRecent.column])
            }
            
            else if(currentPlayer=='a'&&bot==true){
                mostRecent.setToken(whoseTurn,mostRecent);

            }
            else if(currentPlayer=='b'){
                mostRecent.setToken(whoseTurn,mostRecent);
                cell[mostRecent.row][mostRecent.column].setToken('O',cell[mostRecent.row][mostRecent.column]);

            }


        }
    }



}



    private boolean isFull() {
        if(
                        (cellTwo[0][0].token=='X' || cellTwo[0][0].token=='O') &&
                        (cellTwo[0][1].token=='X' || cellTwo[0][1].token=='O') &&
                        (cellTwo[0][2].token=='X' || cellTwo[0][2].token=='O') &&
                        (cellTwo[0][3].token=='X' || cellTwo[0][3].token=='O') &&
                        (cellTwo[0][4].token=='X' || cellTwo[0][4].token=='O') &&
                        (cellTwo[0][5].token=='X' || cellTwo[0][5].token=='O') &&
                        (cellTwo[0][6].token=='X' || cellTwo[0][6].token=='O')



        ){
            return true;
        }
        return false;
    }



    @Override
    public void start(Stage mainStage) throws Exception{
        gameStart=false;
        bot=false;
        primaryStage=mainStage;

        //CONSTRUCTION ZONE AHEAD

        //Labels
        //Welcome Label
        Label welcomeLabel= new Label("Welcome to Connect4");
        welcomeLabel.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        welcomeLabel.setFont(new Font("American Typewriter",80.0));
        welcomeLabel.setAlignment(Pos.BASELINE_CENTER);
        primaryStage.setTitle("Connect4");
        //Victory Label
        victoryLabel=new Label("blank");
        victoryLabel.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        victoryLabel.setAlignment(Pos.BASELINE_CENTER);

        Label singlePlayerLabel= new Label("Choose the intelligence of your opponent");
        singlePlayerLabel.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        singlePlayerLabel.setFont(new Font("American Typewriter",40.0));
        singlePlayerLabel.setAlignment(Pos.BASELINE_CENTER);

        Label themeLabel=new Label("Choose your theme");
        themeLabel.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        themeLabel.setFont(new Font("American Typewriter",80));

        Label labelP1=new Label("Player One's Turn");
        labelP1.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        labelP1.setFont(new Font("American Typewriter",80.0));

        Label labelP2=new Label("Player Two's Turn");
        labelP2.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        labelP2.setFont(new Font("American Typewriter",80.0));

        //BUTTONS:
        //"Singleplayer" button
        Button singlePlayerButton= new Button("Singleplayer");
        singlePlayerButton.setMaxSize(400,50.0);
        singlePlayerButton.setAlignment(Pos.CENTER);
        //"Multiplayer" button
        Button multiplayerButton=new Button("Multiplayer");
        multiplayerButton.setMaxSize(400,50.0);
        multiplayerButton.setAlignment(Pos.CENTER);
        //"easyButton" button
        Button easyButton= new Button("easy");
        easyButton.setMaxSize(400,50.0);
        easyButton.setAlignment(Pos.CENTER);
        //"mediumButton" button
        Button mediumButton=new Button("medium");
        mediumButton.setMaxSize(400,50.0);
        mediumButton.setAlignment(Pos.CENTER);
        //"hardButton" button
        Button hardButton=new Button("hard");
        hardButton.setMaxSize(400,50.0);
        hardButton.setAlignment(Pos.CENTER);
        //"Main Menu" go back button
        Button goBack=new Button("Main Menu");
        goBack.setMaxSize(400,50.0);
        goBack.setAlignment(Pos.CENTER);
        //"Exit" button
        Button exitButton=new Button("Exit");
        exitButton.setMaxSize(400,50.0);
        exitButton.setAlignment(Pos.CENTER);

        Button exitVButton=new Button("Exit");
        exitVButton.setMaxSize(400,50.0);
        exitVButton.setAlignment(Pos.CENTER);
        //Board Button
       
        //Spooky Button
        Button spookyButton=new Button("spooky");
        spookyButton.setTextFill(Color.BLACK);
        spookyButton.setMaxSize(400,50.0);
        //Play Again Button
        Button playAgain=new Button("Play Again");
        playAgain.setMaxSize(400,50.0);
        playAgain.setAlignment(Pos.CENTER);
        //Theme Menu Button
        Button themeButton=new Button("Theme Menu");
        themeButton.setMaxSize(400,50);
        themeButton.setAlignment(Pos.BASELINE_CENTER);
        //Alien Button
        Button alienButton=new Button("alien");
        alienButton.setTextFill(Color.LIMEGREEN);
        alienButton.setMaxSize(400,50.0);
        //Human Button
        Button humanButton=new Button("human");
        humanButton.setTextFill(Color.WHITE);
        humanButton.setMaxSize(400,50.0);

        //Backgrounds and Labels
        Background spookyBack=new Background(new BackgroundFill(Color.DARKRED,new CornerRadii(50.0),spookyButton.getInsets()));
        Background humanBack=new Background(new BackgroundFill(Color.LIGHTPINK,new CornerRadii(10.0),humanButton.getInsets()));
        Background alienBack=new Background(new BackgroundFill(Color.BLACK,new CornerRadii(1.0),alienButton.getInsets()));
        Font spookyFontButton=new Font("Bodoni 72 Oldstyle Bold",40.0);
        Font humanFontButton=new Font("American Typewriter Condensed Bold",40.0);
        Font alienFontButton=new Font("Monospaced Bold",40.0);
        Font spookyFontLabel=new Font("Bodoni 72 Oldstyle Bold",50.0);
        Font humanFontLabel=new Font("American Typewriter Condensed Bold",50.0);
        Font alienFontLabel=new Font("Monospaced Bold",50.0);

        alienButton.setBackground(alienBack);
        spookyButton.setBackground(spookyBack);
        humanButton.setBackground(humanBack);
        spookyButton.setFont(spookyFontButton);
        humanButton.setFont(humanFontButton);
        alienButton.setFont(alienFontButton);


        //SCENES:
        //Victory Scene
        VBox layoutVictory=new VBox(100);
        layoutVictory.getChildren().addAll(victoryLabel,playAgain,exitVButton,boardButton);
        layoutVictory.setAlignment(Pos.BASELINE_CENTER);
        victoryMenu=new Scene(layoutVictory,1200,800);
        //Theme Scene
        VBox themeScene=new VBox(100);
        themeScene.getChildren().addAll(themeLabel,spookyButton,humanButton,alienButton);
        themeLabel.setAlignment(Pos.BASELINE_CENTER);
        themeScene.setAlignment(Pos.BASELINE_CENTER);
        themeMenu=new Scene(themeScene,1200,800);

        //MAIN MENU SCENE

        VBox layout1 = new VBox(80);
        layout1.getChildren().addAll(welcomeLabel, singlePlayerButton,multiplayerButton,themeButton,exitButton);
        layout1.setAlignment(Pos.BASELINE_CENTER);
        mainMenu= new Scene(layout1, 1200, 800);

        //PVB Difficulty decision Scene
        VBox layout2= new VBox(80);
        layout2.setAlignment(Pos.BASELINE_CENTER);
        layout2.getChildren().addAll(singlePlayerLabel, easyButton, mediumButton, hardButton, goBack);
        singlePlayerMenu= new Scene(layout2,1200,800);

        //BUTTON RESPONSES
        boardButton.setOnAction((ActionEvent event)->{
            primaryStage.setScene(playerOneStage);
            primaryStage.show();
        });
        playAgain.setOnAction((ActionEvent event)->{
            bot=false;
            easyBot=false;
            mediumBot=false;
            hardBot=false;
            hb=null;
            hb=new hardBotClass();
            assembleBoards();
            primaryStage.setScene(mainMenu);
            primaryStage.show();
        });
        themeButton.setOnAction((ActionEvent event)->{
            primaryStage.setScene(themeMenu);
            primaryStage.show();
        });
        spookyButton.setOnAction((ActionEvent event)->{
            welcomeLabel.setFont(spookyFontLabel);
            welcomeLabel.setTextFill(Color.DARKVIOLET);
            victoryLabel.setFont(spookyFontLabel);
            victoryLabel.setTextFill(Color.DARKVIOLET);
            singlePlayerLabel.setFont(spookyFontLabel);
            singlePlayerLabel.setTextFill(Color.DARKVIOLET);
            labelP1.setFont(spookyFontLabel);
            labelP1.setTextFill(Color.DARKVIOLET);
            labelP1.setAlignment(Pos.BASELINE_LEFT);
            labelP2.setFont(spookyFontLabel);
            labelP2.setTextFill(Color.DARKVIOLET);


            singlePlayerButton.setTextFill(Color.BLACK);
            singlePlayerButton.setFont(spookyFontButton);
            singlePlayerButton.setBackground(spookyBack);

            multiplayerButton.setTextFill(Color.ORANGE);
            multiplayerButton.setFont(spookyFontButton);
            multiplayerButton.setBackground(spookyBack);

            themeButton.setTextFill(Color.BLACK);
            themeButton.setFont(spookyFontButton);
            themeButton.setBackground(spookyBack);

            exitButton.setTextFill(Color.ORANGE);
            exitButton.setBackground(spookyBack);
            exitButton.setFont(spookyFontButton);

            exitVButton.setTextFill(Color.ORANGE);
            exitVButton.setBackground(spookyBack);
            exitVButton.setFont(spookyFontButton);

            easyButton.setTextFill(Color.BLACK);
            easyButton.setFont(spookyFontButton);
            easyButton.setBackground(spookyBack);

            mediumButton.setTextFill(Color.ORANGE);
            mediumButton.setFont(spookyFontButton);
            mediumButton.setBackground(spookyBack);

            hardButton.setTextFill(Color.BLACK);
            hardButton.setFont(spookyFontButton);
            hardButton.setBackground(spookyBack);

            goBack.setTextFill(Color.ORANGE);
            goBack.setFont(spookyFontButton);
            goBack.setBackground(spookyBack);

            playAgain.setTextFill(Color.ORANGE);
            playAgain.setFont(spookyFontButton);
            playAgain.setBackground(spookyBack);

            layout1.setId("spooky");
            layout2.setId("spooky");
            layoutVictory.setId("spooky");

            imageGenerator(singlePlayerMenu);
            imageGenerator(mainMenu);
            imageGenerator(playerOneStage);
            imageGenerator(playerTwoStage);
            imageGenerator(victoryMenu);
            primaryStage.setScene(mainMenu);
            primaryStage.show();

        });
        humanButton.setOnAction((ActionEvent event)->{
            welcomeLabel.setFont(humanFontLabel);
            welcomeLabel.setTextFill(Color.LIGHTPINK);
            victoryLabel.setFont(humanFontLabel);
            victoryLabel.setTextFill(Color.LIGHTPINK);
            singlePlayerLabel.setFont(humanFontLabel);
            singlePlayerLabel.setTextFill(Color.LIGHTPINK);

            labelP1.setFont(humanFontLabel);
            labelP1.setTextFill(Color.LIGHTPINK);
            labelP1.setAlignment(Pos.BASELINE_LEFT);
            labelP2.setFont(humanFontLabel);
            labelP2.setTextFill(Color.LIGHTPINK);


            singlePlayerButton.setTextFill(Color.WHITE);
            singlePlayerButton.setFont(humanFontButton);
            singlePlayerButton.setBackground(humanBack);

            multiplayerButton.setTextFill(Color.WHITE);
            multiplayerButton.setFont(humanFontButton);
            multiplayerButton.setBackground(humanBack);

            themeButton.setTextFill(Color.WHITE);
            themeButton.setFont(humanFontButton);
            themeButton.setBackground(humanBack);

            exitButton.setTextFill(Color.WHITE);
            exitButton.setBackground(humanBack);
            exitButton.setFont(humanFontButton);

            exitVButton.setTextFill(Color.WHITE);
            exitVButton.setBackground(humanBack);
            exitVButton.setFont(humanFontButton);

            easyButton.setTextFill(Color.WHITE);
            easyButton.setFont(humanFontButton);
            easyButton.setBackground(humanBack);

            mediumButton.setTextFill(Color.WHITE);
            mediumButton.setFont(humanFontButton);
            mediumButton.setBackground(humanBack);

            hardButton.setTextFill(Color.WHITE);
            hardButton.setFont(humanFontButton);
            hardButton.setBackground(humanBack);

            goBack.setTextFill(Color.WHITE);
            goBack.setFont(humanFontButton);
            goBack.setBackground(humanBack);

            playAgain.setTextFill(Color.WHITE);
            playAgain.setFont(humanFontButton);
            playAgain.setBackground(humanBack);

            layout1.setId("human");
            layout2.setId("human");
            layoutVictory.setId("human");

            imageGenerator(singlePlayerMenu);
            imageGenerator(mainMenu);
            imageGenerator(playerOneStage);
            imageGenerator(playerTwoStage);
            imageGenerator(victoryMenu);
            primaryStage.setScene(mainMenu);
            primaryStage.show();

        });
        alienButton.setOnAction((ActionEvent event)->{
            welcomeLabel.setFont(alienFontLabel);
            welcomeLabel.setTextFill(Color.LIMEGREEN);
            victoryLabel.setFont(alienFontLabel);
            victoryLabel.setTextFill(Color.LIMEGREEN);
            singlePlayerLabel.setFont(alienFontLabel);
            singlePlayerLabel.setTextFill(Color.LIMEGREEN);

            labelP1.setFont(alienFontLabel);
            labelP1.setTextFill(Color.LIMEGREEN);
            labelP1.setAlignment(Pos.BASELINE_LEFT);
            labelP2.setFont(alienFontLabel);
            labelP2.setTextFill(Color.LIMEGREEN);

            singlePlayerButton.setTextFill(Color.LIMEGREEN);
            singlePlayerButton.setFont(alienFontButton);
            singlePlayerButton.setBackground(alienBack);

            multiplayerButton.setTextFill(Color.LIMEGREEN);
            multiplayerButton.setFont(alienFontButton);
            multiplayerButton.setBackground(alienBack);

            themeButton.setTextFill(Color.LIMEGREEN);
            themeButton.setFont(alienFontButton);
            themeButton.setBackground(alienBack);

            exitButton.setTextFill(Color.LIMEGREEN);
            exitButton.setBackground(alienBack);
            exitButton.setFont(alienFontButton);

            exitVButton.setTextFill(Color.LIMEGREEN);
            exitVButton.setBackground(alienBack);
            exitVButton.setFont(alienFontButton);

            easyButton.setTextFill(Color.LIMEGREEN);
            easyButton.setFont(alienFontButton);
            easyButton.setBackground(alienBack);

            mediumButton.setTextFill(Color.LIMEGREEN);
            mediumButton.setFont(alienFontButton);
            mediumButton.setBackground(alienBack);

            hardButton.setTextFill(Color.LIMEGREEN);
            hardButton.setFont(alienFontButton);
            hardButton.setBackground(alienBack);

            goBack.setTextFill(Color.LIMEGREEN);
            goBack.setFont(alienFontButton);
            goBack.setBackground(alienBack);

            playAgain.setTextFill(Color.LIMEGREEN);
            playAgain.setFont(alienFontButton);
            playAgain.setBackground(alienBack);

            layout1.setId("alien");
            layout2.setId("alien");
            layoutVictory.setId("alien");

            imageGenerator(singlePlayerMenu);
            imageGenerator(mainMenu);
            imageGenerator(playerOneStage);
            imageGenerator(playerTwoStage);
            imageGenerator(victoryMenu);
            primaryStage.setScene(mainMenu);
            primaryStage.show();

        });
        easyButton.setOnAction((ActionEvent event)->{
            easyBot=true;
            bot=true;
            gameStart=true;
            primaryStage.setScene(playerOneStage);
            primaryStage.show();

        });
        hardButton.setOnAction((ActionEvent event)->{
            hardBot=true;
            bot=true;
            gameStart=true;
            primaryStage.setScene(playerOneStage);
            primaryStage.show();


        });
        mediumButton.setOnAction((ActionEvent event)->{
            mediumBot = true;
            bot=true;
            gameStart=true;
            primaryStage.setScene(playerOneStage);
            primaryStage.show();
        });
        singlePlayerButton.setOnAction((ActionEvent event)-> {
            primaryStage.setScene(singlePlayerMenu);
            primaryStage.show();
        });
        multiplayerButton.setOnAction((ActionEvent event)->{
            gameStart=true;
            primaryStage.setScene(playerOneStage);
            primaryStage.show();

        });
        exitButton.setOnAction((ActionEvent event)-> {
            primaryStage.close();


        });
        exitVButton.setOnAction((ActionEvent event)-> {
            primaryStage.close();


        });
        goBack.setOnAction((ActionEvent event)->{
            primaryStage.setScene(mainMenu);
        });
        //Start here :)
        assembleBoards();
        primaryStage.setScene(themeMenu);
        primaryStage.show();
        }


    public boolean findAvailable(int row, int col){

        if(currentPlayer=='a') {
            if (cell[row][col].isAvailable() == true) {
                mostRecent = cell[row][col];
                return true;
            }
            for (int j = row; j < 6; j++) {
                if (cell[j][col].isAvailable() == true) {
                    mostRecent = cell[j][col];
                    return true;

                }
            }
        }



        else if(currentPlayer=='b'){
            if(cellTwo[row][col].isAvailable()==true) {
                mostRecent=cellTwo[row][col];
                return true;
            }
                for(int j=row;j<6;j++){
                    if(cellTwo[j][col].isAvailable()==true){
                        mostRecent=cellTwo[j][col];
                        return true;

                    } }
        }

        return false;

    }
    public boolean isWon(){
        int check;
        int row=mostRecent.row;
        int col= mostRecent.column;
        int f=0;
        char tok=mostRecent.token;
        int k;
        
        if(tok=='X') {
            //Vertical
            try {

                for (int i = row; i <= 5; i++) {


                    if (cell[i][col].token == tok) f++;
                    else break;
                }

                if (f >= 4) {

                    return true;
                }
                f = 1;
                //Check Horizontal Win

                for (int i = col + 1; i <= 6; i++) {
                    if (cell[row][i].token == tok) f++;
                    else {
                        break;
                    }
                }

                for (int i = col - 1; i >= 0; i--) {
                    if (cell[row][i].token == tok) f++;
                    else break;
                }
                if (f >= 4) {

                    return true;
                }

            } catch (ArrayIndexOutOfBoundsException e) {

            }


            //Check diagonal

            f = 0;
            k = col;

            try {
                //Down right
                k=col;
                row=mostRecent.row;

                for (int i = row; i <= 5; i++) {
                    if (cell[i][k].token == tok) {
                        f++;
                        k++;
                    } else {
                        break;

                    }

                }

                //Up Left

                check = col - 1;

                for (int i = row - 1; i >= 0; i--) {
                    if (cell[i][check].token == tok) {
                        f++;
                        check--;
                    } else {
                        break;
                    }}
                if (f >= 4) {

                    return true;
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {

            }


            //Up Right
            f=0;
            k = col;
            try {
                for (int j = row; j >= 0; j--) {

                    if (cell[j][k].token == tok) {
                        f++;
                        k++;
                    } else {
                        break;
                    }

                }
            } catch (Exception e) {

            }

            check = col-1;
            try {

                //Down Left
                for (int i = row+1; i <= 5; i++) {
                    if (cell[i][check].token == tok) {
                        f++;
                        check--;

                    } else {
                        break;
                    }

                }
            } catch (ArrayIndexOutOfBoundsException e) {

            }
            if (f >= 4) { 
                return true;
            }

        }
        else{
            //Vertical
            try {
                f=0;

                for (int i = row; i <= 5; i++) {


                    if (cellTwo[i][col].token == tok) f++;
                    else break;
                }

                if (f >= 4) {

                    return true;
                }
                f = 1;
                //Check Horizontal Win

                for (int i = col + 1; i <= 6; i++) {
                    if (cellTwo[row][i].token == tok) f++;
                    else {
                        break;
                    }
                }

                for (int i = col - 1; i >= 0; i--) {
                    if (cellTwo[row][i].token == tok) f++;
                    else break;
                }
                if (f >= 4) {

                    return true;
                }

            } catch (ArrayIndexOutOfBoundsException e) {

            }


            //Check diagonal


            f = 0;
            k = col;

            try {
                //Down right
                k = col;

                for (int i = row; i <= 5; i++) {
                    if (cellTwo[i][k].token == tok) {
                        f++;
                        k++;
                    } else {
                        break;

                    }

                }



            } catch (Exception e) {


            }
            try {


                //Up Left

                check = col - 1;

                for (int i = row - 1; i >= 0; i--) {
                    if (cellTwo[i][check].token == tok) {
                        f++;
                        check--;
                    } else {
                        break;
                    }

                }

                if (f >= 4) {

                    return true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {

            }


            //Up Right
            f=0;
            k = col;
            try {
                for (int j = row; j >= 0; j--) {

                    if (cellTwo[j][k].token == tok) {
                        f++;
                        k++;
                    } else {
                        break;
                    }

                }
            } catch (Exception e) {

            }

            check = col-1;
            try {

                //Down Left
                for (int i = row+1; i <= 5; i++) {
                    if (cellTwo[i][check].token == tok) {
                        f++;
                        check--;

                    } else {
                        break;
                    }

                }
            } catch (ArrayIndexOutOfBoundsException e) {

            }
            if (f >= 4) {
                return true;
            }


        }



        return false;
    }


        public void sendTurn(){
            if(isFull()==true){
                victoryLabel.setText("StaleMate");
                primaryStage.setScene(victoryMenu);
                primaryStage.show();


            }

        String winner=" ";
            if(currentPlayer=='a'){
                winner="Player 1";

            }
            else if(currentPlayer=='b'){
                winner="Player 2";

            }


            if(isWon()==true){
                whoseTurn= 'X';
                currentPlayer= 'a';
                victoryLabel.setText(winner+" has  won the game!");
                primaryStage.setScene(victoryMenu);
                primaryStage.show();
            }
            else if(currentPlayer=='a'){
                if(bot){


                    if(easyBot){
                        whoseTurn = 'O';
                        currentPlayer = 'b';
                        cellTwo[mostRecent.row][mostRecent.column].setToken('X',cellTwo[mostRecent.row][mostRecent.column]);
                        easyBotTurn();
                        mostRecent.setToken(whoseTurn,mostRecent);
                        cell[mostRecent.row][mostRecent.column].setToken('O',cell[mostRecent.row][mostRecent.column]);


                    }
                    else if(mediumBot){

                        whoseTurn = 'O';
                        currentPlayer = 'b';
                        cellTwo[mostRecent.row][mostRecent.column].setToken('X',cellTwo[mostRecent.row][mostRecent.column]);
                        mediumBotTurn();
                        mostRecent.setToken(whoseTurn,mostRecent);
                        cell[mostRecent.row][mostRecent.column].setToken('O',cell[mostRecent.row][mostRecent.column]);
                        avoid=mostRecent;


                    }
                    else if(hardBot){
                        whoseTurn = 'O';
                        currentPlayer = 'b';
                        cellTwo[mostRecent.row][mostRecent.column].setToken('X',cellTwo[mostRecent.row][mostRecent.column]);
                        hb.playTurn();
                        mostRecent.setToken(whoseTurn,mostRecent);
                        cell[mostRecent.row][mostRecent.column].setToken('O',cell[mostRecent.row][mostRecent.column]);


                    }

                }
                else {
                    whoseTurn = 'O';
                    currentPlayer = 'b';
                    primaryStage.setScene(playerTwoStage);
                    primaryStage.show();
                }






            }
            else if(currentPlayer=='b'){


                whoseTurn='X';
                currentPlayer='a';
                primaryStage.setScene(playerOneStage);
                primaryStage.show();
            }




    }
    private class hardBotClass{
        /**
         * The avoid arrays contain the target row & column at positions 2 and 3.
         * The row and column that will be avoided in correlation to the target spot
         * are at positions 0 and 1.
         */
        private int [] avoidOne;
        private int [] avoidTwo;
        private int [] avoidThree;
        private int [] avoidFour;


        hardBotClass(){
            this.avoidOne=new int[4];
            this.avoidTwo=new int[4];
            this.avoidThree=new int[4];
            this.avoidFour=new int[4];

            avoidOne[0]=-1;
            avoidTwo[0]=-1;
            avoidThree[0]=-1;
            avoidFour[0]=-1;

            avoidOne[1]=-1;
            avoidTwo[1]=-1;
            avoidThree[1]=-1;
            avoidFour[1]=-1;

            avoidOne[2]=-1;
            avoidTwo[2]=-1;
            avoidThree[2]=-1;
            avoidFour[2]=-1;

            avoidOne[3]=-1;
            avoidTwo[3]=-1;
            avoidThree[3]=-1;
            avoidFour[3]=-1;


        }
        private void updateTargets(){
            try {
                if (cellTwo[avoidOne[0]][avoidOne[1]].token == 'X' || cellTwo[avoidOne[0]][avoidOne[1]].token == 'O') {
                    avoidOne[0] = -1;
                    avoidOne[1] = -1;


                }
            }
            catch (ArrayIndexOutOfBoundsException e){}
            try {
                if (cellTwo[avoidOne[2]][avoidOne[3]].token == 'X' || cellTwo[avoidOne[2]][avoidOne[3]].token == 'O') {
                    avoidOne[2] = -1;
                    avoidOne[3] = -1;

                }
            }
            catch (ArrayIndexOutOfBoundsException e){}
            try {
                if (cellTwo[avoidTwo[0]][avoidTwo[1]].token == 'X' || cellTwo[avoidTwo[0]][avoidTwo[1]].token == 'O') {
                    avoidTwo[0] = -1;
                    avoidTwo[1] = -1;

                }
            }
            catch (ArrayIndexOutOfBoundsException e){}
            try {
                if (cellTwo[avoidTwo[2]][avoidTwo[3]].token == 'X' || cellTwo[avoidTwo[2]][avoidTwo[3]].token == 'O') {
                    avoidTwo[2] = -1;
                    avoidTwo[3] = -1;

                }
            }
            catch (ArrayIndexOutOfBoundsException e){}
            try {
                if (cellTwo[avoidThree[0]][avoidThree[1]].token == 'X' || cellTwo[avoidThree[0]][avoidThree[1]].token == 'O') {
                    avoidThree[0] = -1;
                    avoidThree[1] = -1;

                }
            }
            catch (ArrayIndexOutOfBoundsException e){}
            try {
                if (cellTwo[avoidThree[2]][avoidThree[3]].token == 'X' || cellTwo[avoidThree[2]][avoidThree[3]].token == 'O') {
                    avoidThree[2] = -1;
                    avoidThree[3] = -1;

                }
            }
            catch (ArrayIndexOutOfBoundsException e){}
            try {
                if (cellTwo[avoidFour[0]][avoidFour[1]].token == 'X' || cellTwo[avoidFour[0]][avoidFour[1]].token == 'O') {
                    avoidFour[0] = -1;
                    avoidFour[1] = -1;

                }
            }
            catch (ArrayIndexOutOfBoundsException e){}
            try {
                if (cellTwo[avoidFour[2]][avoidFour[3]].token == 'X' || cellTwo[avoidFour[2]][avoidFour[3]].token == 'O') {
                    avoidFour[2] = -1;
                    avoidFour[3] = -1;

                }
            }
            catch (ArrayIndexOutOfBoundsException e){}



        }
        private void fillAvoid(int [] avoid, int row, int col){
            avoid[0]=row;
            avoid[1]=col;
        }
        private int[] findAvoid(){
            if(avoidOne[0] < 0){
                return avoidOne;
            }
            else if(avoidTwo[0] < 0){
                return avoidTwo;
            }
            else if(avoidThree[0] < 0){
                return avoidThree;
            }
            else if(avoidFour[0] < 0){
                return avoidFour;
            }
            return null;
        }
        private boolean targetChecker(){
            try {
                if (cellTwo[avoidOne[2]][avoidOne[3]].isAvailable() && avoidIt(avoidOne[2],avoidOne[3])) {
                    mostRecent = cellTwo[avoidOne[2]][avoidOne[3]];
                    return true;
                }
            }
            catch (ArrayIndexOutOfBoundsException e){}
            try {
                if (cellTwo[avoidTwo[2]][avoidTwo[3]].isAvailable()&& avoidIt(avoidTwo[2],avoidTwo[3])) {
                    mostRecent = cellTwo[avoidTwo[2]][avoidTwo[3]];
                    return true;
                }
            }
            catch (ArrayIndexOutOfBoundsException e){}
            try {
                if (cellTwo[avoidThree[2]][avoidThree[3]].isAvailable()&& avoidIt(avoidThree[2],avoidThree[3])) {
                    mostRecent = cellTwo[avoidThree[2]][avoidThree[3]];
                    return true;
                }
            }
            catch (ArrayIndexOutOfBoundsException e){}
            try {
                if (cellTwo[avoidFour[2]][avoidFour[3]].isAvailable() && avoidIt(avoidFour[2],avoidFour[3])) {
                    mostRecent = cellTwo[avoidFour[2]][avoidFour[3]];
                    return true;
                }
            }
            catch (ArrayIndexOutOfBoundsException e){}



            return false;
        }

        public void upgradedTarget(char direction){
            int row=targetSpot.row;
            int col=targetSpot.column;
            target=false;
            int [] av;
            if(findAvoid()==null){
                return;
            }
            av=findAvoid();
            av[2]=row;
            av[3]=col;

            if(direction=='W'){
                if(cellTwo[row][col].isAvailable()){
                    mostRecent=cellTwo[row][col];
                    target=true;
                    return;
                }
                else if(cellTwo[row+2][col-1].isAvailable()){
                    mostRecent=cellTwo[row+2][col];
                    target=true;
                    return;

                }
                else{
                    if(findAvoid()!= null){
                        fillAvoid(av,row+1,col);
                    }

                }

            }
            else if(direction=='E'){
                if(cellTwo[row][col].isAvailable()){
                    mostRecent=cellTwo[row][col];
                    target=true;
                    return;
                }
                else if(cellTwo[row+2][col].isAvailable()){
                    mostRecent=cellTwo[row+2][col];
                    target=true;
                    return;

                }

            
                    if(findAvoid()!= null){
                        fillAvoid(av,row+1,col);
                        return;
                    }




            }

            else if(direction=='T'|| direction=='P'){
                if(cellTwo[row][col].isAvailable() ){
                        mostRecent = cellTwo[row][col];
                        target=true;
                        return;

                }
                else if(cellTwo[row+2][col].isAvailable()==true){
                    mostRecent = cellTwo[row+2][col];
                    target=true;
                    return;
                }

                   
                    if(findAvoid()!= null){
                        fillAvoid(av,row+1,col);

                    }   }

        }
        public boolean avoidIt(int row, int col){

            if((row != avoidOne[0] && col!= avoidOne[1]) &&
                    (row != avoidTwo[0] && col!= avoidTwo[1]) &&
                    (row != avoidThree[0] && col!= avoidThree[1]) &&
                    (row != avoidFour[0] && col!= avoidFour[1])){
                return  true;
            }
            return false;

        }
        public Cell betterSpaceFinder(){

            for(int i=5;i>=0;i--){

                for(int j=0;j<=6;j++){
                    try {
                        if (cellTwo[i][j].isAvailable() && avoidIt(i, j)) {
                            if (cellTwo[i - 1][j - 1].token != 'X' && cellTwo[i - 1][j + 1].token != 'X') {
                                return cellTwo[i][j];

                            }


                        }
                    }
                    catch (ArrayIndexOutOfBoundsException e){}
                }

            }
            return null;


        }

        public void playTurn(){

            updateTargets();
            if(targetChecker()){
                return;
            }
            int row=mostRecent.row;
            int col=mostRecent.column;
            char tok='X';
            int vertical=blockCount("vertical");
            int horizontal=blockCount("horizontal");
            int diagonal=blockCount("diagonal");
            int f=0;
            int k;
            for(int i=5;i>=0;i--){

                for(int j=0;j<=6;j++){
                    if(cellTwo[i][j].token=='O'){
                        tok='O';

                        //Vertical
                        try {

                            for (int r = i; r >= 0; r--) {
                                if (cellTwo[r][j].token == tok) f++;
                                else break;
                            }

                            if (f >= 3) {
                                if (cellTwo[i-3][j].isAvailable() ) {
                                    mostRecent = cellTwo[i - 3][j];
                                    return;
                                }
                            }
                        }
                        catch (ArrayIndexOutOfBoundsException r){}

                        try {
                            f = 1;
                            //Horizontal
                            //East
                            for (int r = j + 1; r <= 6; r++) {
                                if (cellTwo[i][r].token == tok) f++;
                                else {
                                    break;
                                }
                            }
                            if (f >= 3) {

                                targetSpot = cellTwo[i][j+1];
                                upgradedTarget('E');
                                if(target==true) {

                                    return;}

                            }
                        }
                        catch (ArrayIndexOutOfBoundsException e){}

                        try{
                            f=1;
                            //West
                            for (int r = j - 1; r >= 0; r--) {
                                if (cellTwo[i][r].token == tok) f++;
                                else break;
                            }
                            if (f >= 2) {
                                targetSpot = cellTwo[i][j - 1];
                                upgradedTarget('W');
                                if(target==true) {

                                    return;
                                }
                            }

                        } catch (ArrayIndexOutOfBoundsException e) {

                        }
                        //Check diagonal


                        f = 0;
                        k = j;
                        //Up Right

                        try {
                            for (int r = i; r >= 0; r--) {

                                if (cellTwo[r][k].token == tok) {
                                    f++;
                                    k++;
                                } else {
                                    break;
                                }

                            }
                            if (f >= 3) {
                                targetSpot = cellTwo[i - 3][j + 3];
                                upgradedTarget('T');
                                if(target==true) return;

                            }
                        } catch (Exception e) {

                        }
                        //Up left
                        f = 0;
                        int check = j;
                        try {

                            for (int r = i; r >= 0; r--) {
                                if (cellTwo[r][check].token == tok) {
                                    f++;
                                    check--;
                                } else {
                                    break;
                                }

                            }

                            if (f >= 3) {

                                targetSpot = cellTwo[i - 3][j - 3];
                                upgradedTarget('P');
                                if(target==true) return;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {

                        }




                    }



                    else if(cellTwo[i][j].token=='X'){
                        tok='X';
                    
                        //Vertical
                        f=0;
                        try {
                           

                            for (int r = i; r >= 0; r--) {
                                if (cellTwo[r][j].token == tok) f++;
                                else break;
                            }

                            if (f >= 3) {
                             


                                if (cellTwo[i-3][j].isAvailable() ) {
                                    mostRecent = cellTwo[i - 3][j];
                                    return;
                                }
                            }
                        }
                        catch (ArrayIndexOutOfBoundsException r){}
                       
                        try {
                            f = 1;
                            //Horizontal
                            //East
                            for (int r = j + 1; r <= 6; r++) {
                                if (cellTwo[i][r].token == tok) f++;
                                else {
                                    break;
                                }
                            }
                            if (f >= 3) {
                               
                                targetSpot = cellTwo[i][j+3];
                      
                                upgradedTarget('E');
                                if(target==true) {
                                   
                                    return;}

                            }
                        }
                        catch (ArrayIndexOutOfBoundsException e){}

                        try{
                            f=1;
                            //West
                            for (int r = j - 1; r >= 0; r--) {
                                if (cellTwo[i][r].token == tok) f++;
                                else break;
                            }
                            if (f >= 2) {
                               
                                targetSpot = cellTwo[i][j - 3];
                                upgradedTarget('W');
                                if(target==true) {
                                    return;
                                }
                            }

                        } catch (ArrayIndexOutOfBoundsException e) {

                        }
                        //Check diagonal
                     
                        f = 0;
                        k = j;
                        //Up Right

                        try {
                            for (int r = i; r >= 0; r--) {

                                if (cellTwo[r][k].token == tok) {
                                    f++;
                                    k++;
                                } else {
                                    break;
                                }

                            }
                            if (f >= 3) {
                                targetSpot = cellTwo[i - 3][j + 3];
                                upgradedTarget('T');
                                if(target==true) return;

                            }
                        } catch (Exception e) {

                        }
                        //Up left
                        f = 0;
                        int check = j;
                        try {

                            for (int r = i; r >= 0; r--) {
                                if (cellTwo[r][check].token == tok) {
                                    f++;
                                    check--;
                                } else {
                                    break;
                                }

                            }

                            if (f >= 3) {
                                targetSpot = cellTwo[i - 3][j - 3];
                                upgradedTarget('P');
                                if(target==true) return;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {}
                    }

                }

            }

            char dir;
            String [] ordered=order(vertical,horizontal,diagonal);
            try {

                if (ordered[2].equals("vertical")) {

                    if (cellTwo[row-1][col].isAvailable()) {
                        mostRecent = cellTwo[row-1][col];
                        return;
                    }



                }
            }
            catch (ArrayIndexOutOfBoundsException e){

            }
            try {


                if (ordered[2].equals("horizontal")) {

                    //To the right
                    target=false;
                    dir = direction();
                    if (dir == 'e') {
                        targetSpot = cellTwo[row][col + 1];
                        upgradedTarget('E');
                    } else if (dir == 'w') {
                        targetSpot = cellTwo[row][col - 1];
                        upgradedTarget('W');}}
                }

            catch(Exception e){

            }
            try {
                //NE=T
                //NW=P
                if (ordered[2].equals("diagonal")) {
                    target=false;


                        dir = direction();
                        if (dir == 'T') {
                            targetSpot = cellTwo[row - 1][col + 1];
                            upgradedTarget('T');


                        }
                        else if (dir == 'Y') {
                         
                            targetSpot = cellTwo[row - 1][col - 1];
                            upgradedTarget('P');


                        }

                    }


            }
            catch (Exception  e){

            }


            if(target==false){
               
                try {
                    mostRecent = betterSpaceFinder();
                   
                }
            catch (NullPointerException e){
                    mostRecent=spaceFinder();
            }
            }

         



        }





    }
         public void assembleBoards(){
             Label labelP1=new Label("Player One's Turn");
             labelP1.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
             labelP1.setFont(new Font("American Typewriter",80.0));

             Label labelP2=new Label("Player Two's Turn");
             labelP2.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
             labelP2.setFont(new Font("American Typewriter",80.0));
            playerOne = new GridPane();
             for (int i = 0; i < 6; i++){
                 for (int j = 0; j < 7; j++){
                     playerOne.add(cell[i][j] = new Cell(), j, i);
                     cell[i][j].setColumn(j);
                     cell[i][j].setRow(i);
                     cell[i][j].setAvailable(false);}}


             BorderPane borderPaneOne = new BorderPane();
             cell[5][0].setAvailable(true);
             cell[5][1].setAvailable(true);
             cell[5][2].setAvailable(true);
             cell[5][3].setAvailable(true);
             cell[5][4].setAvailable(true);
             cell[5][5].setAvailable(true);
             cell[5][6].setAvailable(true);

             cell[0][0].setTop();
             cell[0][1].setTop();
             cell[0][2].setTop();
             cell[0][3].setTop();
             cell[0][4].setTop();
             cell[0][5].setTop();
             cell[0][6].setTop();


             borderPaneOne.setCenter(playerOne);
             borderPaneOne.setBottom(labelP1);


             playerOneStage = new Scene(borderPaneOne, 1200, 800);




             playerTwo = new GridPane();

             for (int i = 0; i < 6; i++){
                 for (int j = 0; j < 7; j++){
                     playerTwo.add(cellTwo[i][j] = new Cell(), j, i);
                     cellTwo[i][j].setColumn(j);
                     cellTwo[i][j].setRow(i);
                     cellTwo[i][j].setAvailable(false);}}


             BorderPane borderPaneTwo = new BorderPane();
             cellTwo[5][0].setAvailable(true);
             cellTwo[5][1].setAvailable(true);
             cellTwo[5][2].setAvailable(true);
             cellTwo[5][3].setAvailable(true);
             cellTwo[5][4].setAvailable(true);
             cellTwo[5][5].setAvailable(true);
             cellTwo[5][6].setAvailable(true);

             cellTwo[0][0].setTop();
             cellTwo[0][1].setTop();
             cellTwo[0][2].setTop();
             cellTwo[0][3].setTop();
             cellTwo[0][4].setTop();
             cellTwo[0][5].setTop();
             cellTwo[0][6].setTop();


             borderPaneTwo.setCenter(playerTwo);
             borderPaneTwo.setBottom(labelP2);

             avoid=cellTwo[5][6];


             playerTwoStage = new Scene(borderPaneTwo, 1200, 800);

         }

         public void imageGenerator(Scene obj){
            Random random=new Random();
            int rand=random.nextInt(5);

            if(rand==0){
                obj.getStylesheets().addAll(this.getClass().getResource("backgroundOne.css").toExternalForm());
            }
            else if(rand==2){
                obj.getStylesheets().addAll(this.getClass().getResource("backgroundTwo.css").toExternalForm());
            }
            else if(rand==3){
                obj.getStylesheets().addAll(this.getClass().getResource("backgroundThree.css").toExternalForm());
            }
            else if(rand==4){
                obj.getStylesheets().addAll(this.getClass().getResource("backgroundFour.css").toExternalForm());
            }
            else if(rand==5){
                obj.getStylesheets().addAll(this.getClass().getResource("backgroundFive.css").toExternalForm());
            }
            else{
                obj.getStylesheets().addAll(this.getClass().getResource("backgroundFive.css").toExternalForm());

            }

    }
        public void easyBotTurn(){
            int row=mostRecent.row;
            int col=mostRecent.column;
            int vertical=blockCount("vertical");
            int horizontal=blockCount("horizontal");
            int diagonal=blockCount("diagonal");
            String [] ordered=order(vertical,horizontal,diagonal);


            try {

                if (ordered[2].equals("vertical")) {

                    for (int i = row - 1; i >= 0; i--) {
                        if (cellTwo[i][col].isAvailable()) {
                            mostRecent = cellTwo[i][col];
                            return;
                        }
                    }

                }
            }
            catch (ArrayIndexOutOfBoundsException e){}
            try {

                if (ordered[2].equals("horizontal")) {

                    //To the right
                    char dir = direction();


                    if (dir == 'e' || dir == 'x') {
                        for (int i = col + 1; i <= 6; i++) {
                            if (cellTwo[row][i].isAvailable()) {
                                mostRecent = cellTwo[row][i];
                                return;
                            }

                        }
                    } else if (dir == 'w' || dir == 'w') {
                        for (int i = col - 1; i >= 0; i--) {
                            if (cellTwo[row][i].isAvailable()) {
                                mostRecent = cellTwo[row][i];
                                return;
                            }

                        }
                    }
                }
            }
                catch(ArrayStoreException e){


                }
                mostRecent=spaceFinder();








        }
        public Cell spaceFinder(){

            for(int i=5;i>=0;i--){

                for(int j=0;j<=6;j++){

                    if(cellTwo[i][j].isAvailable()&&(i != avoid.row && j!= avoid.column) ){
                        return cellTwo[i][j];

                    }
                }

            }
            return null;


        }
        public void avoid(int row, int col){
            avoid=cellTwo[row][col];

        }
        public void target(char direction){
            int row=targetSpot.row;
            int col= targetSpot.column;


            target=true;


           if(direction=='W'){

               if(cellTwo[row][col].isAvailable()){

                   mostRecent=cellTwo[row][col];
                   target=false;
               }
               else if(cellTwo[row+2][col-1].isAvailable()){
                   mostRecent=cellTwo[row+2][col-1];

               }
               else{
                   avoid(row+1,col-1);

               }

            }
            else if(direction=='E'){
               if(cellTwo[row][col].isAvailable()){

                   mostRecent=cellTwo[row][col];
                   target=false;
               }
               else if(cellTwo[row+2][col+1].isAvailable()){
                   mostRecent=cellTwo[row+2][col+1];

               }
               else{
                   avoid(row+1,col+1);


               }


            }

            else if(direction=='T'|| direction=='P'){
                if(cellTwo[row][col].isAvailable()){
                    if(!cellTwo[row+1][col].isAvailable()){
                        mostRecent = cellTwo[row][col];
                        target=false;

                    }
                    else if(cellTwo[row+2][col].isAvailable()){
                        mostRecent = cellTwo[row][col];
                        target=false;
                    }

                }

            }









        }
        public void mediumBotTurn(){
            int row=mostRecent.row;
            int col=mostRecent.column;
            int vertical=blockCount("vertical");
            int horizontal=blockCount("horizontal");
            int diagonal=blockCount("diagonal");
            char dir;
            String [] ordered=order(vertical,horizontal,diagonal);




            try {

                if (ordered[2].equals("vertical")) {

                        if (cellTwo[row-1][col].isAvailable()) {


                            mostRecent = cellTwo[row-1][col];
                            return;
                            
                        }



                }
            }
            catch (Exception e){

            }
            try {


                if (ordered[2].equals("horizontal")) {

                    //To the right
                    dir = direction();




                    if (dir == 'e') {
                        if(target==true){

                            target('E');
                            if(target==false){
                                return;

                            }
                        }
                        else {
                            targetSpot = cellTwo[row][col + 1];
                            target('E');
                            if (target == false) {
                                return;
                            }
                        }
                    } else if (dir == 'w') {
                        if(target==true){
                            targetSpot = cellTwo[row][col - 1];
                            target('W');
                            if(target==false){
                                return;
                            }
                        }
                        else {

                            targetSpot = cellTwo[row][col - 1];
                            target('W');
                            if (target == false) {
                                return;
                            }
                        }

                    }
                }
            }
                catch(Exception e){

                }
            try {
                //NE=T
                //NW=P
                if (ordered[2].equals("diagonal")) {

                    if (target == true) {
                        target('T');
                        if(target==false){
                            return;
                        }

                    }
                    else{


                    boolean check;
                    dir = direction();
                    if (dir == 'T') {
                        if (target == false) {
                            targetSpot = cellTwo[row - 1][col + 1];
                        }
                        target('T');
                        if (target == false) {
                            return;
                        }


                    } else if (dir == 'P') {

                        if (target == false) {
                            targetSpot = cellTwo[row - 1][col - 1];
                        }
                        target('P');
                        if (target == false) {
                            return;
                        }

                    }

                }


                }
            }
            catch (Exception  e){

            }
                mostRecent=spaceFinder();

        }
        //returns direction of input
        public char direction(){
            int row=mostRecent.row;
            int col= mostRecent.column;
            char direction;
            int east=0;
            int west=0;
            int vert=0;

                //east
            try {
                for (int i = col; i <= 6; i++) {
                    if (cellTwo[row][i].getToken() == 'X') {
                        east++;
                    } else {
                        break;
                    }

                }
            }
                catch(Exception e){

                }
                try {
                    //west
                    for (int i = col; i >= 0; i--) {
                        if (cellTwo[row][i].getToken() == 'X') {
                           west++;
                        } else {
                            break;
                        }

                    }

                    for (int i = row; i >= 0; i--) {
                        if (cellTwo[col][i].getToken() == 'X') {
                            vert++;
                        } else {
                            break;
                        }

                    }
                }
                catch (Exception e){

                }
                //NE
                //NE=t
                try {

                    if ((cellTwo[row][col].token == 'X') && (cellTwo[row + 1][col - 1].token == 'X') && (cellTwo[row + 2][col - 2].token == 'X')) {

                        return 'T';
                    }
                }
                catch (Exception e) {

                }
                try{


                    //NW
                    //NW=Y
                    if ((cellTwo[row][col].token == 'X') && (cellTwo[row + 1][col + 1].token == 'X') && (cellTwo[row + 2][col + 2].token == 'X')) {
                        return 'Y';

                    }

                }
                catch (Exception e){

                }

                if (east >west && east > vert) {
                    return 'w';
                } else if (west > east &&west > vert) {
                    return 'e';
                } else if (vert > east && vert >west) {
                    return 'n';
                }
                else if(east>west && east==vert){
                    return 'w';

                }
                else if(east>vert && east==west){
                    return 'w';
            }
                else if(west> east&&west==vert){
                    return 'e';


                }
                else if(west > vert &&west==east){
                    return 'e';

                }
                else if(vert > east && vert==west){
                    return 'n';

                }
                else if(vert >west && vert==east){
                    return 'n';

                }





            return 'x';

        }

        //Returns ordered trio largest to smallest
        public String[] order(int v, int h, int d){
            String[] ordered=new String[3];
            int[] order=new int[3];
            int vTemp=v;
            int hTemp=h;
            int dTemp=d;

            if (vTemp > hTemp) {
                int temp = vTemp;
                vTemp = hTemp;
                hTemp = temp;
            }

            if (hTemp > dTemp) {
                int temp = hTemp;
                hTemp = dTemp;
                dTemp = temp;
            }

            if (vTemp > hTemp) {
                int temp = vTemp;
                vTemp = hTemp;
                hTemp = temp;
            }
            order[0]=vTemp;
            order[1]=hTemp;
            order[2]=dTemp;

            if(order[0]==v){
                ordered[0]="vertical";
                if(order[1]==d){
                    ordered[1]="diagonal";
                    ordered[2]="horizontal";

                }
                else if(order[1]==h){
                    ordered[1]="horizontal";
                    ordered[2]="diagonal";

                }
            }
            else if(order[0]==h){
                ordered[0]="horizontal";
                if(order[1]==d){
                    ordered[1]="diagonal";
                    ordered[2]="vertical";

                }
                else if(order[1]==v){
                    ordered[1]="vertical";
                    ordered[2]="diagonal";

                }
            }
            else if(order[0]==d){
                ordered[0]="diagonal";
                if(order[1]==h){
                    ordered[1]="horizontal";
                    ordered[2]="vertical";

                }
                else if(order[1]==v){
                    ordered[1]="vertical";
                    ordered[2]="horizontal";

                }
            }



            return ordered;

        }


        public int blockCount(String direction){

            int row=mostRecent.row;
            int col=mostRecent.column;
            int count=0;
            try {


                if (direction.equals("vertical")) {
                    for (int c = row; c >= 0; c++) {
                        if (cellTwo[c][col].getToken() == 'X') {
                            count++;


                        } else {
                            break;
                        }
                    }
                    return count;


                } else if (direction.equals("horizontal")) {
                    //to the right
                    for (int c = col; c <= 6; c++) {
                        if (cellTwo[row][c].getToken() == 'X') {
                            count++;
                        } else {
                            break;
                        }

                    }
                    if (count >= 2) {
                        return count;
                    }
                    //to the left
                    for (int c = col - 1; c >= 0; c--) {
                        if (cellTwo[row][c].getToken() == 'X') {
                            count++;
                        } else {
                            break;
                        }
                    }
                    return count;

                }
                else if (direction.equals("diagonal")) {


                    if(cellTwo[row][col].token=='X'){
                        count++;
                        if(cellTwo[row+1][col-1].token=='X'){
                            count++;
                            if(cellTwo[row+2][col-2].token=='X'){
                                count++;
                                return count;

                            }

                        }


                    }
                  count=0;


                    if(cellTwo[row][col].token=='X'){
                        count++;
                        if(cellTwo[row+1][col+1].token=='X'){
                            count++;
                            if(cellTwo[row+2][col+2].token=='X'){
                                count++;
                                return count;
                            }

                        }


                    }
                    return count;



                }
            }
            catch (Exception e){
                return count;
            }
            return count;

        }





         public Paint randomColorGenerator() {
             Random random=new Random();
             int upperbound=999;
             int rand=random.nextInt(upperbound);
             if (rand % 7 <= 0) {
                 return Color.AQUA;
             } else if (rand % 7 <= 1) {
                 return Color.AZURE;
             } else if (rand % 7 <= 2) {
                 return Color.TOMATO;
             } else if (rand % 7 <= 3) {
                 return Color.VIOLET;
             } else if (rand % 7 <= 4) {
                 return Color.THISTLE;
             } else if (rand % 7 <= 5) {
                 return Color.SEAGREEN;
             } else if (rand % 7 <= 6) {
                 return Color.MEDIUMPURPLE;
             }
             return Color.PINK;
         }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
