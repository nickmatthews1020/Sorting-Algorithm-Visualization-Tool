package SortAlgorithms;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.Timeline;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.geometry.*;

public class SortAlgorithmGUI extends Application{
	
	private static final int QUANT = 100;
	private static final int DELAY = 100; // must be at least QUANT + 1
	private int index, index2, index3;
	private int interval, small;
	private int[] data = new int[QUANT];
	private Line[] lines = new Line[QUANT];
	private HBox lower, upper;
	private BorderPane mainPane;
	private Scene scene;
	private Button randomButton, sortButton;
	private Button insertButton;
	
	@Override /* Method in Application class */
	public void start(Stage primaryStage) {
		int sceneWidth = 600, sceneHeight = 350;
		
		initializeLines(data, lines);
		
		randomButton = new Button("Randomize");
		randomButton.setOnAction(e -> {
			
			upper.getChildren().removeAll(lines);
			SortingAlgorithms.shuffle(data);
			translateLines(data, lines);
			upper.getChildren().addAll(lines);
			
		});
		
		insertButton = new Button("Insertion Sort");
		insertButton.setOnAction(e -> {
			
			index3 = data.length - 1;
			Timer timer2 = new Timer();

			TimerTask colorSweep = new TimerTask(){

				public void run() {
					Platform.runLater(new Runnable() {
						public void run() {
							upper.getChildren().removeAll(lines);
							lines[index3].setStroke(Color.LIGHTGREEN);
							upper.getChildren().addAll(lines);
							index3 --;
							if (index3 < 0) {
								timer2.cancel();
							}
						}
					});
				}
			};
			
			Timer timer = new Timer();
			index = data.length - 2;
			timer.scheduleAtFixedRate(new TimerTask() {
				public void run() {
					Platform.runLater(new Runnable() {
						public void run() {
							upper.getChildren().removeAll(lines);
							SortingAlgorithms.insertionSortSteps(data, index);
							translateLines(data, lines);
							upper.getChildren().addAll(lines);
							index --;
							if (index < 0) {
								timer.cancel();
								finish(timer2, colorSweep);
							}
						}
					});
				}
			}, DELAY, DELAY);
		});
		
		sortButton = new Button("Sort");
		sortButton.setOnAction(e -> {
			
			index = data.length - 1;
			Timer timer = new Timer();
			Timer timer2 = new Timer();
			
			TimerTask colorSweep = new TimerTask(){
				
				public void run() {
					Platform.runLater(new Runnable() {
						public void run() {
							upper.getChildren().removeAll(lines);
							lines[index3].setStroke(Color.LIGHTGREEN);
							upper.getChildren().addAll(lines);
							index3 --;
							if (index3 < 0) {
								timer2.cancel();
							}
						}
					});
				}
			};

			interval = DELAY + 1;
			index = data.length-1;
			index3 = data.length-1;
			recurse(timer, timer2, colorSweep);
			
		});
		
		lower = new HBox(10);
		lower.setPadding(new Insets(0, 0, 25, 70));
		lower.getChildren().addAll(randomButton, sortButton, insertButton);
		
		upper = new HBox(1);
		upper.setPadding(new Insets(0, 100, 50, 200));
		upper.getChildren().addAll(lines);
		upper.setRotate(180);
		
		mainPane = new BorderPane();
		mainPane.setBottom(lower);
		mainPane.setTop(upper);
		
		scene = new Scene(mainPane, sceneWidth, sceneHeight);
		primaryStage.setTitle("Sorting Algorithm Visualization Tool");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void recurse(Timer timer, Timer timer2, TimerTask colorSweep) {
		interval --;
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
			    Platform.runLater(new Runnable() {
			       public void run() {
			    	   red(interval);
			    	   int[] steps = SortingAlgorithms.selectionSortSteps(data, index);
			    	   upper.getChildren().removeAll(lines);
			    	   switchLinesStep(data, steps[0], steps[1]);
			    	   translateLines(data, lines);
			    	   lines[steps[0]].setStroke(Color.LIGHTGREEN);
			    	   upper.getChildren().addAll(lines);
			    	   index --;
			    	   timer.cancel();
			    	   if(index == 0) {
			    		   finish(timer2, colorSweep);
			    	   }else {
			    		   recurse(new Timer(), timer2, colorSweep);
			    	   }
			      }
			    });
			}
		}, interval, DELAY);
	}
	
	public void finish(Timer timer2, TimerTask colorSweep) {
		upper.getChildren().removeAll(lines);
		translateLines(data, lines);
		upper.getChildren().addAll(lines);
		timer2.scheduleAtFixedRate(colorSweep, DELAY/5, DELAY/10);
	}
	
	public void red(int time) {
		index2 = index - 1;
		small = Integer.MAX_VALUE;
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
			    Platform.runLater(new Runnable() {
			       public void run() {
			    	   upper.getChildren().removeAll(lines);
			    	   if (index2 >= 0) {
			    		   lines[index2].setStroke(Color.RED);
			    		   if (data[index2] < small) {
			    			   small = data[index2];
			    		   }
			    	   }
			    	   
			    	   upper.getChildren().addAll(lines);
			    	   index2--;
			    	   if (index2 < -1) {
			    		   timer.cancel();
			    		   upper.getChildren().removeAll(lines);
			    		   for (int i = index; i >= 0; i--) {
			    			   if(data[i] > small) {
			    				   lines[i].setStroke(Color.BLACK);
			    			   }
			    		   }
			    		   upper.getChildren().addAll(lines);
			    	   }
			    	   if (index <= 0) {
			    		   upper.getChildren().removeAll(lines);
			    		   translateLines(data, lines);
			    		   upper.getChildren().addAll(lines);
			    	   }
			       }
			    });
			}
			
		};
		
		timer.scheduleAtFixedRate(task, 1, 1);
	}
	
	// lines array and integer array must be same length
	public static void translateLines(int[] data, Line[] lines) {
		for (int i = 0; i < data.length; i++) {
			lines[i] = new Line(0, 0, 0, data[i]);
			lines[i].setStrokeWidth(2);
		}
	}
	
	public static void initializeData(int[] data) {
		for (int i = 0; i < data.length; i ++) {
			data[i] = (2*(data.length - i - 1))/(QUANT/100);
		}
	}
	
	public static void initializeLines(int[] data, Line[] lines) {
		initializeData(data);
		translateLines(data, lines);
	}
	
	public static void switchLinesStep(int[] data, int i1, int i2) {
		int temp = data[i2];
		data[i2] = data[i1];
		data[i1] = temp;
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
}
