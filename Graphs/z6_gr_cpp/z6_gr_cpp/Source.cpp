#include<iostream>
#include<fstream>
#include<cstdio>
#include<vector>
#include <utility>
#include <set>
#include <vector>

using namespace std;

int n;
vector < vector<int> > g;
vector<int> matching;
vector<char> used;

bool isFit(int p, int q, int a, int b) {
	if (p <= a && q <= b)
		return true;
	if (p > a && q <= b) {  
		double temp1 = (double)(a + b) * (a + b) / (p + q) / (p + q);
		double temp2 = (double)(a - b) * (a - b) / (p - q) / (p - q);
		if (temp1 + temp2 >= 2)
			return true;
	}
	return false;
}

bool kuhn(int v) {
	if (used[v])  return false;
	used[v] = true;
	for (size_t i = 0; i < g[v].size(); ++i) {
		int to = g[v][i];

		if (matching[to] == -1 || kuhn(matching[to])) {
			matching[to] = v;
			return true;
		}

	}
	return false;
}


int main() {
	ifstream fin("input.in");
	ofstream fout("output.out");

	fin >> n;

	pair<int, int>* postcards = new pair<int, int>[n]();

	int temp1, temp2;

	for (int i = 0; i < n; ++i) {
		fin >> temp1 >> temp2;
		if (temp1 < temp2)
			std::swap(temp1, temp2);
		postcards[i] = make_pair(temp1, temp2);
	}

	g.resize(n);

	for (size_t i = 0; i < n; ++i)
	{
		fin >> temp1 >> temp2;
		if (temp1 < temp2)
			std::swap(temp1, temp2);
		for (size_t j = 0; j < n; ++j) {
			if (isFit(postcards[j].first, postcards[j].second, temp1, temp2)) {
				g[i].push_back(j);
			}
		}

	}

	matching.assign(n, -1);
	for (int v = 0; v < n; ++v) {
		used.assign(n, false);
		kuhn(v);
	}

	bool yes = true;
	int number = 0;

	for (int i = 0; i < n; ++i) {
		if (matching[i] == -1) {
			yes = false;
		}
		else {
			++number;
		}
	}
	if (yes)
		fout << "YES";
	else {
		fout << "NO\n" << number;
	}

	delete[] postcards;

}