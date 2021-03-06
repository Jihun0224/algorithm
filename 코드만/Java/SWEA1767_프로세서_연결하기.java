package _0403;

import java.io.*;
import java.util.*;

public class SWEA1767_프로세서_연결하기 {
	static int map[][],n,totalC,answer[];
	static int[]dx= {1,-1,0,0};
	static int[]dy= {0,0,1,-1};
	static ArrayList<int[]>core;
	static void dfs(int depth,int curCore,int curlen) {
		//현재 선택된 코어와 선택할 수 있는 코어를 합한 것 보다 현재 정답 후보의 코어수가 더 크면 더 볼 필요가 없음 
		if(answer[1]>curCore+totalC-depth) return;
		//전체 코어를 다 방문 한 경우
		if(depth==totalC) {
			if(answer[1]<curCore) {
				answer[0]=curlen;
				answer[1]=curCore;
			}
			else if(answer[1]==curCore) {
				answer[0]=Math.min(answer[0], curlen);
			}
			return;
		}
		//코어의 위치
		int[]temp=core.get(depth);
		int cx=temp[0],cy=temp[1];
		//4방 탐색으로 경우의 수 모두 확인
		for(int i=0;i<4;i++) {
			int cnt=isPos(cx, cy, i);
			//코어연결이 가능할 때
			if(cnt!=-1) {
				change(cx, cy, 2, i,cnt);
				dfs(depth+1, curCore+1,curlen+cnt-1);
				change(cx, cy, 0, i,cnt);
			}
		}	
		dfs(depth+1, curCore,curlen);
	}
	//코어를 연결한 전선 상태 변경
	static void change(int x,int y,int state,int d,int cnt) {
		for(int i=1;i<cnt;i++) {
			int nx=x+dx[d]*i;
			int ny=y+dy[d]*i;
			map[nx][ny]=state;
		}
	}
	//가장자리에 연결 가능한지 확인
	static int isPos(int x,int y,int d) {
		int cnt=1;
		while(true) {
			int nx=x+dx[d]*cnt;
			int ny=y+dy[d]*cnt;
			//전선을 설치할 자리에 코어가 있거나 이미 전선이 있으면 설치 X
			if(map[nx][ny]!=0)return -1;
			//설치 가능
			if(nx==0||ny==0||nx==n+1||ny==n+1)return cnt;
			cnt++;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		int T=Integer.parseInt(br.readLine());
		for(int t=1;t<=T;t++) {
			answer=new int[2];
			answer[0]=Integer.MAX_VALUE;
			totalC=0;
			core=new ArrayList<>();
			st=new StringTokenizer(br.readLine());
			n=Integer.parseInt(st.nextToken());
			//가장자리 체크 편의성을 위해 열과 행+2
			map=new int[n+2][n+2];
			for(int i=1;i<=n;i++) {
				st=new StringTokenizer(br.readLine());
				for(int j=1;j<=n;j++) {
					map[i][j]=Integer.parseInt(st.nextToken());
					if(map[i][j]==1) {
						//가장 자리 코어는 이미 전선이 연결된 상태이므로 고려 X
						if(i==1||j==1||i==n||j==1) {
							map[i][j]=2;
						}
						else {
							core.add(new int[]{i,j});
							totalC++;
						}
					}
				}
			}
			dfs(0, 0, 0);
			sb.append("#"+t+" "+ answer[0]+"\n");
		}
		bw.write(sb.toString());
		bw.flush();
	}
}