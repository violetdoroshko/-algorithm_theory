#include<iostream>
#include<cstring>
#include<algorithm>
#include<fstream>

using namespace std;


//const int MAXN = ...; // число вершин
const int INF = 1000000000; // константа-бесконечность

int n, c[MAXN][MAXN], f[MAXN][MAXN], s, t, d[MAXN], ptr[MAXN], q[MAXN];
bool bsf();
int dfs(int v, int flow);

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


bool bfs() {
	int qh = 0, qt = 0;
	q[qt++] = s;
	memset(d, -1, n * sizeof d[0]);
	d[s] = 0;
	while (qh < qt) {
		int v = q[qh++];
		for (int to = 0; to < n; ++to)
			if (d[to] == -1 && f[v][to] < c[v][to]) {
				q[qt++] = to;
				d[to] = d[v] + 1;
			}
	}
	return d[t] != -1;
}

int dfs(int v, int flow) {
	if (!flow)  return 0;
	if (v == t)  return flow;
	for (int& to = ptr[v]; to < n; ++to) {
		if (d[to] != d[v] + 1)  continue;
		int pushed = dfs(to, min(flow, c[v][to] - f[v][to]));
		if (pushed) {
			f[v][to] += pushed;
			f[to][v] -= pushed;
			return pushed;
		}
	}
	return 0;
}

int dinic() {
	int flow = 0;
	for (;;) {
		if (!bfs())  break;
		memset(ptr, 0, n * sizeof ptr[0]);
		while (int pushed = dfs(s, INF))
			flow += pushed;
	}
	return flow;
}