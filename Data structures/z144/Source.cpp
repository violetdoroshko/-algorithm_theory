#define _CRT_SECURE_NO_WARNINGS
#include<iostream>
#include<fstream>
#include <queue>
#include<cstdio>

bool operator<(const std::pair<double, int>& x, std::pair<double, int>& y)
{
	return x.first < y.first;
}

struct CompleteTree {

	std::priority_queue<std::pair<double, int>> leafs;

	CompleteTree() {
		std::pair<double, int> temp(1, 0);
		leafs.push(temp);
	}

	double getAverageLength(int k, double* a, long d_n) {
		double length = 0;

		while ((leafs.size() - 1 + k) <= d_n) {
			std::pair<double, int> temp = leafs.top();
			leafs.pop();
			length -= temp.first * temp.second;

			for (int i = 0; i < k; ++i) {
				std::pair<double, int> son(temp.first * a[i], temp.second + 1);
				length += son.first * son.second;
				leafs.push(son);
			}
		}
		return length;
	}
};

long my_pow(int d, int n) {
	long temp = 1;
	for (int i = 0; i < n; ++i)
		temp *= d;
	return temp;
}

int main() {
	std::ios_base::sync_with_stdio(false);

	freopen("Tunstall.in", "r", stdin);
	freopen("Tunstall.out", "w", stdout);


	int d;
	int k;
	int n;

	scanf("%d %d %d", &d, &k, &n);

	double* a = new double[k]();
	for (int i = 0; i < k; ++i)
		scanf("%lf", &a[i]);

	CompleteTree completeTree;

	double result = completeTree.getAverageLength(k, a, my_pow(d, n));
	printf("%f", result);

	delete[] a;
	return 0;
}