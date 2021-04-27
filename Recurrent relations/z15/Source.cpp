#include<iostream>

#include<fstream>

using namespace std;

long long MIN = -9223372036854775808LL;
long long MAX = 9223372036854775807LL;


class MinAndMax {
public:
	long long min = MAX;
	long long max = MIN;
	MinAndMax() {}
};

long long findMax(int n, long long* d) {
	long long  max = MIN;
	for (int i = 0; i < n; ++i) {
		if (max < d[i])
			max = d[i];
	}

	return max;
}

long long doOperation(long long  number1, char operation, long long  number2) {

	if (operation == 't')
		return number1 + number2;
	else
		return number1 * number2;
}


MinAndMax f(int i, int j, MinAndMax** matrix, long long* numbers, char* operations) {
	if (i == j) {
		matrix[i][j].min = numbers[i];
		matrix[i][j].max = numbers[i];
	}
	else if (matrix[i][j].max == MIN && matrix[i][j].min == MAX) {


		long long  max = MIN;
		long long  min = MAX;
		long long* temp = new long long[4];
		for (int k = i; k < j; ++k) {
			MinAndMax left = f(i, k, matrix, numbers, operations);
			MinAndMax right = f(k + 1, j, matrix, numbers, operations);

			temp[0] = doOperation(left.max, operations[k + 1], right.max);
			temp[1] = doOperation(left.max, operations[k + 1], right.min);
			temp[2] = doOperation(left.min, operations[k + 1], right.max);
			temp[3] = doOperation(left.min, operations[k + 1], right.min);

			for (int l = 0; l < 4; ++l) {
				if (max < temp[l])
					max = temp[l];
				else if (min > temp[l])
					min = temp[l];
			}
		}
		matrix[i][j].max = max;
		matrix[i][j].min = min;

	}
	return matrix[i][j];
}



void main() {

	std::ifstream fin("input.txt");
	std::ofstream fout("output.txt");

	int n;
	fin >> n;

	long long* numbers = new long long[2 * n];
	char* operations = new char[2 * n];

	for (int i = 0; i < n; ++i) {
		fin >> operations[i];
		operations[i + n] = operations[i];
		fin >> numbers[i];
		numbers[i + n] = numbers[i];
	}


	MinAndMax** matrix = new MinAndMax * [2 * n - 1];
	long long* maxNumbers = new long long[n];
	for (int i = 0; i < 2 * n - 1; ++i) {
		matrix[i] = new MinAndMax[2 * n - 1];
	}


	for (int i = 0; i < n; ++i)
		maxNumbers[i] = f(i, n + i - 1, matrix, numbers, operations).max;


	long long  max = findMax(n, maxNumbers);

	fout << max << '\n';

	bool isWritten = false;
	for (int i = 0; i < n; ++i) {
		if (maxNumbers[i] == max) {
			if (isWritten)
				fout << ' ';
			fout << i + 1;
			isWritten = true;
		}
	}
	delete[] numbers;
	delete[] operations;
	delete[] maxNumbers;
	for (int i = 0; i < 2 * n - 1; i++)
		delete[] matrix[i];
	delete[] matrix;
}