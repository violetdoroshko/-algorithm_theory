#include<iostream>
#include<fstream>
#include<cstdio>
#include<vector>
#include<queue>
#include<algorithm>
#include<set>

using namespace std;

const long long int INF = 9223372036854775807;

long long int* dijkstra(int n, int s, vector<vector<pair<int, int>>> g) {

	long long int* dist = new long long int[n];

	for (int i = 0; i < n; ++i) {
		dist[i] = INF;
	}
	dist[s] = 0;

	set<pair<long long int, int>> temp;		//путь, вершина
	temp.insert(make_pair(0, s));

	while (!temp.empty())
	{
		int x = (*temp.begin()).second;
		temp.erase(temp.begin());

		for (int i = 0; i < g[x].size(); ++i)
		{
			long long int length = g[x][i].second;
			int y = g[x][i].first;

			if (dist[y] > dist[x] + length) {
				temp.erase(make_pair(dist[y], y));
				dist[y] = dist[x] + length;
				temp.insert(make_pair(dist[y], y));
			}
		}
	}
	return dist;
}

int main() {
	ifstream fin("input.txt");
	ofstream fout("output.txt");

	int n, m;
	fin >> n >> m;

	vector<vector<pair<int, int>>> g(n);

	long temp1, temp2, temp3;

	for (size_t i = 0; i < m; ++i)
	{
		fin >> temp1 >> temp2 >> temp3;
		g[temp1].push_back(make_pair(temp2, temp3));
		g[temp2].push_back(make_pair(temp1, temp3));
	}

	int* s = new int[3];

	for (int i = 0; i < 3; ++i) {
		fin >> s[i];
	}

	long long int min = INF;
	long long int temp;

	long long int* d0 = dijkstra(n, s[0], g);
	long long int* d1 = dijkstra(n, s[1], g);
	long long int* d2 = dijkstra(n, s[2], g);

	for (int i = 0; i < n; ++i) {
		temp = d0[i] + d1[i] + d2[i];

		if (temp < min)
			min = temp;
	}

	fout << min;
}