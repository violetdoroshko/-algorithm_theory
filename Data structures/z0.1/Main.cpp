#include <iostream>

using namespace std;

int lower_bound(int* arr, int n, int number, int l, int r) {

	int k;

	while (l < r) {
		k = (l + r) / 2;
		if (number <= arr[k]) {
			r = k;
		}
		else {
			l = k + 1;
		}
	}

	return l;
}

int upper_bound(int* arr, int n, int number, int l, int r) {

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

void binary_search(int* arr, int n, int number) {

	int l = 0;
	int r = n;
	int k;

	while (l < r) {
		k = (l + r) / 2;
		if (arr[k] == number) {

			cout << "1 " << lower_bound(arr, n, number, l, k) << ' ' << upper_bound(arr, n, number, k+1, r) << '\n';
			return;
		}
		else if (number < arr[k])
			r = k;
		else
			l = k + 1;
	}

	cout << "0 " << l << ' ' << l << '\n';
}

void main() {

	int n;
	cin >> n;

	int* arr = new int[n];

	for (int i = 0; i < n; ++i)
		cin >> arr[i];

	int k;
	cin >> k;

	int number;

	for (int i = 0; i < k; ++i) {
		cin >> number;
		binary_search(arr, n, number);
	}

	//system("pause");

}