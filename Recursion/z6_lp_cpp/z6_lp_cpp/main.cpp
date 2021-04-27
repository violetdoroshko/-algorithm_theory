#include<iostream>
#include<fstream>

using namespace std;

int upper_bound(long* arr, int n, long number, int l, int r) {
	int k;
	while (l < r) {
		k = (l + r) / 2;
		if (number >= arr[k]) {
			l = k + 1;
		}
		else {
			r = k;
		}
	}
	return r;
}

int longestIncreasingSubsequenceLength(long* numbers, int n) {

	if (n == 1)
		return 1;

	int length = 0;

	long* subsequence = new long[n];

	for (int i = 0; i < n; ++i)
		subsequence[i] = 1000000001;

	subsequence[0] = numbers[0];

	int index;

	for (int i = 1; i < n; ++i) {
		index = upper_bound(subsequence, n, numbers[i], 0, length + 1);

		if (index == 0)
			subsequence[0] = numbers[i];
		else if (subsequence[index - 1] != numbers[i]) {
			subsequence[index] = numbers[i];
			if (index > length)
				++length;
		}
	}
	return length + 1;
}

void main() {

	std::ifstream fin("input.txt");
	std::ofstream fout("output.txt");
	
	int n ;
	fin >> n;
	long* numbers = new long[n];

	for (int i = 0; i < n; ++i)
		fin>>numbers[i];

	int length = longestIncreasingSubsequenceLength(numbers, n);

	fout << length;
}