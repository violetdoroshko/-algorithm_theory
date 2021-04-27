#include<iostream>
#include<fstream>
#include<cstdio>
#include<vector>
#include<queue>
#include<algorithm>
#include<set>

using namespace std;

const long long int INF = 9223372036854775807;

void dijkstra(int n, int s, long long int* dist, vector<vector<pair<int, int>>> g) {

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
}

int main() {
	ifstream fin("input.txt");
	ofstream fout("output.txt");

	long n, m;
	fin >> n >> m;

	vector<vector<pair<int, int>>> g(n);

	int temp1, temp2, temp3;

	for (size_t i = 0; i < m; ++i)
	{
		fin >> temp1 >> temp2 >> temp3;
		g[temp1 - 1].push_back(make_pair(temp2 - 1, temp3));
		g[temp2 - 1].push_back(make_pair(temp1 - 1, temp3));
	}

	long long int* dist = new long long int[n];
	for (int i = 0; i < n; ++i)
		dist[i] = INF;;
	dist[0] = 0;

	dijkstra(n, 0, dist, g);

	fout << dist[n - 1];

}