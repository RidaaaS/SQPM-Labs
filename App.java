package com.ontariotechu.sofe3980U;

import java.io.FileReader;
import java.util.List;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

public class App {
	public static void main(String[] args) {
		String filePath = "model.csv"; // Path to the CSV file

		try (FileReader filereader = new FileReader(filePath);
			 CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build()) {

			List<String[]> allData = csvReader.readAll(); // Read all data from the CSV file

			// Initialize variables for Cross-Entropy and Confusion Matrix
			double crossEntropy = 0.0;
			int[][] confusionMatrix = new int[5][5]; // 5x5 confusion matrix for 5 classes

			// Iterate through each row in the CSV file
			for (String[] row : allData) {
				int trueClass = Integer.parseInt(row[0]); // True class (1 to 5)
				double[] predictedProbabilities = new double[5]; // Predicted probabilities for each class

				// Parse predicted probabilities
				for (int j = 0; j < 5; j++) {
					predictedProbabilities[j] = Double.parseDouble(row[j + 1]);
				}

				// Calculate Cross-Entropy
				crossEntropy += -Math.log(predictedProbabilities[trueClass - 1]);

				// Determine predicted class (class with the highest probability)
				int predictedClass = 0;
				double maxProbability = predictedProbabilities[0];
				for (int j = 1; j < 5; j++) {
					if (predictedProbabilities[j] > maxProbability) {
						maxProbability = predictedProbabilities[j];
						predictedClass = j;
					}
				}

				// Update Confusion Matrix
				confusionMatrix[trueClass - 1][predictedClass]++;
			}

			// Normalize Cross-Entropy
			crossEntropy /= allData.size();

			// Print Cross-Entropy
			System.out.println("CE = " + crossEntropy);

			// Print Confusion Matrix
			System.out.println("Confusion Matrix:");
			System.out.println("                y=1      y=2     y=3     y=4     y=5");
			for (int i = 0; i < 5; i++) {
				System.out.print("        y^=" + (i + 1) + "    ");
				for (int j = 0; j < 5; j++) {
					System.out.print(confusionMatrix[i][j] + "     ");
				}
				System.out.println();
			}

		} catch (Exception e) {
			System.out.println("Error reading the CSV file: " + e.getMessage());
		}
	}
}
