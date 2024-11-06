package main;

import java.util.Scanner;

public class Quiz1_4 {

    public static void main(String[] args) {
        // Find Treasure
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        int m = s.nextInt();
        int[][] map = new int[n][m];
        for(int i=0 ; i<n ; i++){
            for(int j=0 ; j<m ; j++){
                map[i][j] = s.nextInt();
            }
        }
        
        int max=0;
        int r=0;
        int c=0;
        for(int row=0 ; row<n-1 ; row++){
            for(int col = 0 ; col<m ; col++){
                if(Math.abs(map[row][col] - map[row+1][col]) <= 10){
                    if(map[row][col] > max){
                        max = map[row][col];
                        r = row+1;
                        c = col+1;
                    }else if(map[row+1][col] > max){
                        max = map[row+1][col];
                        r= row+2;
                        c=col+1;
                    }
                }
            }
        }
        System.out.println(r+" " + c );
        
        
    }
    
}