// Diego Land
// Nation management game
// Jack and Anish

import java.util.*;
import java.io.FileWriter;
import java.math.*;

public class DiegoLand {
	
	// IMPORTS
	
	static Scanner scan = new Scanner(System.in);
	static Random rand = new Random();
	
	// VARIABLES
	
	int population = 20;
	int day = 1;
	int time = 0;
	boolean run = true;
	int apd = 4;
	
	int[] tiers = {0, 100, 300, 800, 12000, 30000, 60000, 120000};
	int getTier() {
		
		int _return = -1;
		for (int i = 0; i < tiers.length && _return == -1; i++) {
			
			if (tiers[i] > population) {
				
				_return = i;
				
			}
			
		}
		return _return;
		
	}
	
	// RESOURCES

	int[] rsc = {20000, 500, 100, 150, 20, 50, 30, 5, 0};
	String[] rsc_names = {"Money", "Food", "Power", "Building Materials", "Consumer Goods", "Metal", "Ammunition", "Fuel", "Uranium"};
	
		// NATURAL
		
		int[] rsc_land = new int[4 + 1];
		int[] rsc_fauna = new int[3 + 1];
		int[] rsc_flora = new int[4 + 1];
		int[] rsc_mined = new int[9 + 1];
		
		String[] names_land = {"Forest", "Desert", "Lake", "Grassland", "Cleared Land"};
		String[] names_fauna = {"Bear", "Salmon", "Shark", "Cow"};
		String[] names_flora = {"Berry Bush", "Oak Tree", "Cactus", "Coral", "Apple Tree"};
		String[] names_mined = {"Coal", "Iron", "Copper", "Sulfur", "Saltpeter", "Clay", "Sandstone", "Petroleum", "Crude Oil", "Uraninite"};
		
		double[] stats_land = {0.45, 0.45 + 0.17, 0.45 + 0.17 + 0.2, 0.45 + 0.17 + 0.2 + 0.19};
		double[][] stats_fauna = {{0, 0.2, 1, 4, 3, 0.08, 1, 3}, {2, 0.6, 2, 15}, {2, 0.2, 1, 2}, {3, 0.5, 3, 9}};
		double[][] stats_flora = {{0, 0.75, 1, 6, 3, 0.08, 1, 3}, {0, 0.75, 2, 8, 3, 0.45, 1, 5}, {1, 0.7, 1, 3}, {2, 0.35, 3, 10}, {0, 0.3, 1, 5, 3, 0.45, 1, 5}};
		double[][] stats_mined = {{0, 0.6, 3, 20, 1, 0.65, 3, 20, 2, 0.1, 2, 20, 3, 0.35, 3, 12}, {0, 0.3, 2, 15, 1, 0.65, 1, 18, 3, 0.3, 1, 10}, {0, 0.25, 3, 12, 1, 0.35, 3, 12, 3, 0.1, 2, 8}, {0, 0.4, 5, 35, 1, 0.55, 5, 45, 2, 0.3, 5, 35, 3, 0.1, 5, 30}, {0, 0.1, 3, 8, 1, 0.5, 5, 45, 2, 0.05, 3, 14, 3, 0.3, 3, 10}, {0, 0.05, 3, 30, 1, 0.2, 3, 40, 2, 0.5, 3, 12, 3, 0.05, 3, 20}, {1, 0.9, 3, 35}, {1, 0.3, 3, 18, 2, 0.3, 1, 25}, {0, 0.3, 3, 20, 3, 0.1, 3, 15}, {0, 0.03, 1, 5, 1, 0.08, 1, 8, 2, 0.01, 1, 4, 3, 0.02, 1, 5}};
		
	// FACTORIES
	Factory[] factory_templates = {
			new Factory(new int[][] {{0, 3}, {}, {}}, new int[]{1, 2}, new int[][] {{0, 10}, {}, {0, 1}}, 2, "Berry Picker"),
			new Factory(new int[][] {{0, 4}, {1, 1}, {}}, new int[] {1, 4}, new int[][] {{0, 10}, {2, 1}, {}}, 1, "Salmon Fisher"),
			new Factory(new int[][] {{0, 5}, {}, {}}, new int[] {1, 2}, new int[][] {{0, 15, 3, 5}, {}, {4, 1}}, 2, "Orchard"),
			new Factory(new int[][] {{0, 4, 2, 1}, {3, 1}, {}}, new int[] {1, 6}, new int[][] {{0, 15, 3, 5}, {4, 1}, {}}, 1, "Farm"),
			new Factory(new int[][] {{0, 4}, {}, {}}, new int[] {2, 4}, new int[][] {{0, 10, 3, 10}, {4, 1}, {}}, 1, "Windmill"),
			new Factory(new int[][] {{0, 4}, {}, {}}, new int[] {3, 3}, new int[][] {{0, 10}, {}, {1, 1}}, 1, "Lumberjack"),
			new Factory(new int[][] {{0, 4, 2, 3}, {}, {5, 2}}, new int[] {3, 8}, new int[][] {{0, 8, 3, 10, 5, 2}, {4, 1}, {}}, 2, "Brickworks"),
			new Factory(new int[][] {{0, 6, 2, 2}, {}, {6, 2}}, new int[] {3, 8}, new int[][] {{0, 10, 3, 4}, {1, 1}, {}}, 2, "Sandstone Quarry"),
			new Factory(new int[][] {{0, 10}, {}, {}}, new int[] {2, 12}, new int[][] {{0, 30, 3, 10, 5, 10}, {2, 1}, {}}, 1, "Hydroelectric Plant"),
			new Factory(new int[][] {{0, 5}, {}, {0, 2}}, new int[] {2, 15}, new int[][] {{0, 25, 3, 10, 5, 8}, {4, 1}, {}}, 1, "Power Plant"),
			new Factory(new int[][] {{0, 4}, {}, {}}, new int[] {1, 1, 4, 1}, new int[][] {{0, 6}, {}, {2, 1}}, 1, "Cactus Potter"),
			new Factory(new int[][] {{0, 12}, {0, 1}, {}}, new int[] {1, 5, 4, 3}, new int[][] {{0, 15}, {4, 1}, {}}, 2, "Hunter"),
			new Factory(new int[][] {{0, 10}, {2, 1}, {}}, new int[] {1, 7, 4, 5}, new int[][] {{0, 8, 3, 4}, {2, 1}, {}}, 2, "Shark Trapper"),
			new Factory(new int[][] {{0, 10, 2, 1}, {}, {}}, new int[] {3, 4}, new int[][] {{0, 8, 3, 8, 5, 2}, {1, 1}, {}}, 2, "Jeweler"),
			new Factory(new int[][] {{0, 5, 2, 3}, {}, {1, 2}}, new int[] {5, 5}, new int[][] {{0, 25, 3, 10}, {4, 1}, {}}, 1, "Iron Smelter"),
			new Factory(new int[][] {{0, 6, 2, 4}, {}, {2, 2}}, new int[] {5, 8}, new int[][] {{0, 28, 3, 15}, {4, 1}, {}}, 1, "Copper Mine"),
			new Factory(new int[][] {{0, 8, 2, 5, 5, 1}, {}, {3, 2}}, new int[] {6, 15}, new int[][] {{0, 30, 3, 30, 5, 10}, {4, 1}, {}}, 1, "Ammunition Factory"),
			new Factory(new int[][] {{0, 10, 2, 6, 5, 1}, {}, {4, 2}}, new int[] {6, 25}, new int[][] {{0, 35, 3, 35, 5, 12}, {4, 1}, {}}, 2, "Explosives Factory"),
			new Factory(new int[][] {{0, 10, 2, 8}, {}, {7, 1}}, new int[] {7, 6}, new int[][] {{0, 32, 3, 22, 5, 15}, {4, 1}, {}}, 2, "Petroleum Refinery"),
			new Factory(new int[][] {{0, 10, 2, 8}, {}, {8, 1}}, new int[] {7, 6}, new int[][] {{0, 32, 3, 24, 5, 14}, {4, 1}, {}}, 2, "Crude Oil Refinery"),
			new Factory(new int[][] {{0, 40, 2, 12}, {}, {9, 1}}, new int[] {8, 4}, new int[][] {{0, 100, 3, 150, 5, 60}, {4, 2}, {}}, 1, "Nuclear Reactor"),
			new Factory(new int[][] {{0, 25, 9, 1}, {}, {}}, new int[] {2, 50}, new int[][] {{0, 65, 3, 100, 5, 50}, {4, 2}, {}}, 1, "Nuclear Power Plant")
	};
	int[] factories = new int[factory_templates.length];
	int[] factories_lastCollected = new int[factory_templates.length];
	
	// ALL COMMANDS
		
	void cmd_resources() {
		
		System.out.println("RESOURCES");
		for (int i = 0; i < rsc.length; i++) {
			
			System.out.println(rsc_names[i] + ": " + rsc[i]);
			
		}
		if (day == 1 && time == 0) {
			
			System.out.println();
			System.out.println("Nice job!");
			System.out.println("There are many different commands that perform various actions.");
			System.out.println("You can view all unlocked commands with the HELP command.");
			System.out.println("Your next step should be constructing some factories to help you gain food.");
			System.out.println("Good luck!");
			
		}
		
	}
	
	void cmd_constructf() {
		
		System.out.println("CONSTRUCT FACTORIES");
		int last = 0;
		if (getTier() == 1) {
			
			last = 8;
			
		} else if (getTier() == 2) {
			
			last = 16;
			
		} else if (getTier() == 3) {
			
			last = 18;
			
		} else if (getTier() <= 5) {
			
			last = 20;
			
		} else if (getTier() <= 7) {
			
			last = 22;
			
		}
		for (int i = 0; i < last; i++) {
			
			System.out.print((i + 1) + ") " + factory_templates[i].name + "  ");
			if ((i + 1) % 3 == 0) {
				
				System.out.println();
				
			}
			
		}
		System.out.println();
		System.out.print("Type # to view info/buy, 0 to cancel: ");
		int index = scan.nextInt();
		if (index >= 1 && index <= last) {
			
			String _input = "";
			String _output = "";
			String _cost = "";
			// _input
			for (int i = 0; i < 3; i++) {
				
				if (i == 0) {
					
					for (int j = 0; j < factory_templates[index - 1].input[i].length; j += 2) {
						
						_input += factory_templates[index - 1].input[i][j + 1] + " " + rsc_names[factory_templates[index - 1].input[i][j]] + " ";
						
					}
					
				}
				if (i == 1 && factory_templates[index - 1].input[1].length != 0) {
					
					for (int j = 0; j < factory_templates[index - 1].input[i].length; j += 2) {
						
						_input += factory_templates[index - 1].input[i][j + 1] + " " + names_fauna[factory_templates[index - 1].input[i][j]] + " ";
						
					}
					
				}
				if (i == 2 && factory_templates[index - 1].input[2].length != 0) {
					
					for (int j = 0; j < factory_templates[index - 1].input[i].length; j += 2) {
						
						_input += factory_templates[index - 1].input[i][j + 1] + " " + names_mined[factory_templates[index - 1].input[i][j]] + " ";
						
					}
					
				}
				
			}
			// _output
			for (int i = 0; i < factory_templates[index - 1].output.length; i += 2) {
				
				_output += factory_templates[index - 1].output[i + 1] + " " + rsc_names[factory_templates[index - 1].output[i]] + " ";
				
			}
			//_cost
			for (int i = 0; i < 3; i++) {
				
				if (i == 0) {
					
					for (int j = 0; j < factory_templates[index - 1].cost[i].length; j += 2) {
						
						_cost += factory_templates[index - 1].cost[i][j + 1] + " " + rsc_names[factory_templates[index - 1].cost[i][j]] + " ";
						
					}
					
				}
				if (i == 1 && factory_templates[index - 1].cost[1].length != 0) {
					
					for (int j = 0; j < factory_templates[index - 1].cost[1].length; j += 2) {
					
						_cost += factory_templates[index - 1].cost[i][j + 1] + " " + names_land[factory_templates[index - 1].cost[i][j]] + " ";
						
					}
					
				}
				if (i == 2 && factory_templates[index - 1].cost[2].length != 0) {
					
					for (int j = 0; j < factory_templates[index - 1].cost[i].length; j += 2) {
						
						_cost += factory_templates[index - 1].cost[i][j + 1] + " " + names_flora[factory_templates[index - 1].cost[i][j]] + " ";
						
					}
					
				}
				
			}
			System.out.println(factory_templates[index - 1].name + ": " + _input + "-> " + _output + "(Time: " + factory_templates[index - 1].time + " day(s))");
			System.out.println(_cost + "to construct; you have " + factories[index - 1] + " " + factory_templates[index - 1].name + " already");
			System.out.print("Enter quantity to build, 0 to cancel: ");
			int quantity = scan.nextInt();
			boolean valid = true;
			String used = "";
			// valid, used
			if (quantity < 1) {
				
				System.out.println("CONSTRUCTF cancelled");
				
			} else {
			
				for (int i = 0; i < 3; i++) {
				
					if (i == 0) {
						
						for (int j = 0; j < factory_templates[index - 1].cost[i].length; j += 2) {
							
							if (rsc[factory_templates[index - 1].cost[i][j]] < factory_templates[index - 1].cost[i][j + 1] * quantity) {
								
								valid = false;
								
							} else {
								
								used += (factory_templates[index - 1].cost[i][j + 1] * quantity) + " " + rsc_names[factory_templates[index - 1].cost[i][j]] + " ";
								
							}
							
						}
						
					}
					if (i == 1 && factory_templates[index - 1].cost[1].length != 0) {
						
						for (int j = 0; j < factory_templates[index - 1].cost[1].length; j += 2) {
						
							if (rsc_land[factory_templates[index - 1].cost[i][j]] < factory_templates[index - 1].cost[i][j + 1] * quantity) {
								
								valid = false;
								
							} else {
								
								used += (factory_templates[index - 1].cost[i][j + 1] * quantity) + " " + names_land[factory_templates[index - 1].cost[i][j]] + " ";
								
							}
							
						}
						
					}
					if (i == 2 && factory_templates[index - 1].cost[2].length != 0) {
						
						for (int j = 0; j < factory_templates[index - 1].cost[i].length; j += 2) {
							
							if (rsc_flora[factory_templates[index - 1].cost[i][j]] < factory_templates[index - 1].cost[i][j + 1] * quantity) {
								
								valid = false;
								
							} else {
								
								used += (factory_templates[index - 1].cost[i][j + 1] * quantity) + " " + names_flora[factory_templates[index - 1].cost[i][j]] + " ";
								
							}
							
						}
						
					}
				
				}
				
				if (!valid) {
					
					System.out.println("You don't have enough resources");
					System.out.println("CONSTRUCTF cancelled");
					
				} else {
					
					System.out.println("Bought " + quantity + " " + factory_templates[index - 1].name + " for " + used);
					String left = "";
					for (int i = 0; i < 3; i++) {
						
						if (i == 0) {
							
							for (int j = 0; j < factory_templates[index - 1].cost[i].length; j += 2) {
								
								rsc[factory_templates[index - 1].cost[i][j]] -= factory_templates[index - 1].cost[i][j + 1];
								left += rsc[factory_templates[index - 1].cost[i][j]] + " " + rsc_names[factory_templates[index - 1].cost[i][j]] + " ";
								
							}
							
						}
						if (i == 1 && factory_templates[index - 1].cost[1].length != 0) {
							
							for (int j = 0; j < factory_templates[index - 1].cost[1].length; j += 2) {
							
								rsc_land[factory_templates[index - 1].cost[i][j]] -= factory_templates[index - 1].cost[i][j + 1];
								left += rsc_land[factory_templates[index - 1].cost[i][j]] + " " + names_land[factory_templates[index - 1].cost[i][j]] + " ";
								
							}
							
						}
						if (i == 2 && factory_templates[index - 1].cost[2].length != 0) {
							
							for (int j = 0; j < factory_templates[index - 1].cost[i].length; j += 2) {
								
								rsc_flora[factory_templates[index - 1].cost[i][j]] -= factory_templates[index - 1].cost[i][j + 1];
								left += rsc_flora[factory_templates[index - 1].cost[i][j]] + " " + names_flora[factory_templates[index - 1].cost[i][j]] + " ";
								
							}
							
						}
					
					}
					System.out.println("You now have " + left);
					factories[index - 1]++;
					factories_lastCollected[index - 1] = -1;
					time++;
					
				}
				
			}
			
		} else {
			
			System.out.println("CONSTRUCTF cancelled");
			
		}
		
	}
	
	void cmd_factories() {
		
		System.out.println("FACTORIES");
		for (int i = 0; i < factories.length; i++) {
			
			System.out.println((i + 1) + ") " + factory_templates[i].name + " (" + factories[i] + ")");
			
		}
		
	}
		
	// COMMAND HANDLER
		
	void command(String command) {
		
		switch (command) {
		
		case "RESOURCES":
			cmd_resources();
			break;
			
		case "CONSTRUCT":
			System.out.println("Trying to construct? Use CONSTRUCTF for factories and CONSTRUCTB for buildings.");
			break;
			
		case "CONSTRUCTF":
			cmd_constructf();
			break;
			
		case "FACTORIES":
			cmd_factories();
			break;
			
		default:
			System.out.println("Invalid command entered, run HELP to view a list of commands");
		
		}
		
	}
		
	// GAME LOOP
		
	void GameLoop() {
		
		while (run) {
			
			System.out.println("DAY " + day);
			System.out.println();
			
			if (day == 1) {
				
				System.out.println("Welcome to Diego Land!");
				System.out.println("Your job is to allow your newly generated virtual nation to thrive.");
				System.out.println("You must manage your resources, build up a military, and survive attacks from neighboring rivals.");
				System.out.println("To do this, you can type in various COMMANDS every turn.");
				System.out.println("Start off by typing in the RESOURCES command below.");
				System.out.println();
				
			}
			
			for (time = 0; time < apd;) {
				
				System.out.print("Command: ");
				String command = scan.next();
				System.out.println();
				command(command);
				System.out.println();
				
			}
			
			for (int i = 0; i < factories.length; i++) {
				
				if (factories[i] != 0) {
					
					factories_lastCollected[i]++;
					
				}
				
			}
			scan.nextLine();
			System.out.println("Day is over! Press ENTER to continue to DAY " + (day + 1) + "...");
			scan.nextLine();
			System.out.println("***");
			System.out.println();
			day++;

			try {
				SaveData(population, day, time, apd, rsc, rsc_land, rsc_fauna, rsc_flora, rsc_mined);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	public static void main(String[] args) {
			
		// MAIN
		
		DiegoLand game = new DiegoLand();
		
		System.out.println();
		System.out.println("*** DIEGO LAND 1.0 ***");
		System.out.println("***  MADE BY JACK  ***");
		System.out.println("***   AND  ANISH   ***");
		System.out.println();
		System.out.println("***");
		System.out.println("***");
		System.out.println("***");
		System.out.println();
		
		// GENERATE
		
		System.out.print("Press ENTER to generate resources... ");
		scan.nextLine();
		
		System.out.println("Generating resources...");
		
			// LAND
		
			double rsc_total = 0;
			for (int i = 0; i < 80; i++) {
				
				double prob = rand.nextDouble();
				if (prob < game.stats_land[0]) {
					
					game.rsc_land[0]++;
					
				} else if (prob < game.stats_land[1]) {
					
					game.rsc_land[1]++;
					
				} else if (prob < game.stats_land[2]) {
					
					game.rsc_land[2]++;
					
				} else if (prob < game.stats_land[3]) {
					
					game.rsc_land[3]++;
					
				}
				
			}
			
			// i = land type, j = land tile #, k = rsc #, l = rsc land #, m = # of rsc, n = rsc gen min
			
			for (int i = 0; i < game.rsc_land.length; i++) {
				
				for (int j = 0; j < game.rsc_land[i]; j++) {
					
					for (int k = 0; k < game.stats_fauna.length; k++) {
						
						for (int l = 0; l < game.stats_fauna[k].length; l += 4) {
							
							if (game.stats_fauna[k][l] == (double)i) {
								
								double random = rand.nextDouble();
								if (random < game.stats_fauna[k][l + 1]) {
									
									Double m = new Double(game.stats_fauna[k][l + 3] + 1 - game.stats_fauna[k][l + 2]);
									Double n = new Double(game.stats_fauna[k][l + 2]);
									int add = rand.nextInt(m.intValue()) + n.intValue();
									game.rsc_fauna[k] += add;
									rsc_total += add;
									
								}
								
							}
							
						}
						
					}
					
					for (int k = 0; k < game.stats_flora.length; k++) {
						
						for (int l = 0; l < game.stats_flora[k].length; l += 4) {
							
							if (game.stats_flora[k][l] == (double)i) {
								
								double random = rand.nextDouble();
								if (random < game.stats_flora[k][l + 1]) {
									
									Double m = new Double(game.stats_flora[k][l + 3] + 1 - game.stats_flora[k][l + 2]);
									Double n = new Double(game.stats_flora[k][l + 2]);
									int add = rand.nextInt(m.intValue()) + n.intValue();
									game.rsc_flora[k] += add;
									rsc_total += add;
									
								}
								
							}
							
						}
						
					}
					
					for (int k = 0; k < game.stats_mined.length; k++) {
						
						for (int l = 0; l < game.stats_mined[k].length; l += 4) {
							
							if (game.stats_mined[k][l] == (double)i) {
								
								if (rand.nextDouble() < game.stats_mined[k][l + 1]) {
									
									Double m = new Double(game.stats_mined[k][l + 3] + 1 - game.stats_mined[k][l + 2]);
									Double n = new Double(game.stats_mined[k][l + 2]);
									int add = rand.nextInt(m.intValue()) + n.intValue();
									game.rsc_mined[k] += add;
									rsc_total += add;
									
								}
								
							}
							
						}
						
					}
					
				}
				
			}
			
			// PRINT
			
			System.out.println();
			System.out.println("Land:  | " + game.rsc_land[0] + " " + game.names_land[0] + " | " + game.rsc_land[1] + " " + game.names_land[1] + " | " + game.rsc_land[2] + " " + game.names_land[2] + " | " + game.rsc_land[3] + " " + game.names_land[3] + " |");
			System.out.print("Fauna: | ");
			for (int i = 0; i < game.rsc_fauna.length; i++) {
				
				System.out.print(game.rsc_fauna[i] + " " + game.names_fauna[i] + " | ");
				
			}
			System.out.println();
			System.out.print("Flora: | ");
			for (int i = 0; i < game.rsc_flora.length; i++) {
				
				System.out.print(game.rsc_flora[i] + " " + game.names_flora[i] + " | ");
				
			}
			System.out.println();
			System.out.print("Mined: | ");
			for (int i = 0; i < game.rsc_mined.length; i++) {
				
				System.out.print(game.rsc_mined[i] + " " + game.names_mined[i] + " | ");
				
			}
			System.out.println();
			rsc_total = (rsc_total - 2250) / 1000;
			if (rsc_total > 0) {
				
				System.out.println("Luck: +" + rsc_total);
				
			} else {
			
				System.out.println("Luck: " + rsc_total);
				
			}
		
		System.out.println();
		System.out.println("Press ENTER to continue to DAY 1...");
		scan.nextLine();
		System.out.println("***");
		System.out.println();
		
		game.GameLoop();
		
	}
	
	public static void SaveData(int population, int day, int time, int apd, 
			int[] rsc, int[] rsc_land, int[] rsc_fauna, int[] rsc_flora, int[] rsc_mined)  throws Exception
	{
		String csvFile = "/Diego Land/src/save.csv";
		Saving save = new Saving();
		
		FileWriter writer = new FileWriter(csvFile);
		
		save.Save(population, writer);
		save.Save(day, writer);
		save.Save(time, writer);
		save.Save(apd, writer);
		
		save.Save(rsc, writer);
		save.Save(rsc_land, writer);
		save.Save(rsc_fauna, writer);
		save.Save(rsc_flora, writer);
		save.Save(rsc_mined, writer);
		
		writer.flush();
        writer.close();
	}

}
