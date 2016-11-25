#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>
#define MAX_LINE 100 
#define MAX_ARGS 4

const char *path[] = {"./","/usr/bin/", "/bin/", NULL};

void removeChar(char *str, char garbage) {

    char *src, *dst;
    for (src = dst = str; *src != '\0'; src++) {
        *dst = *src;
        if (*dst != garbage) dst++;
    }
    *dst = '\0';
}

int main( void ){
	char comando[50],*args[MAX_LINE];
	char* limite=" ";
	char* parsing=NULL;
	int contador=1;
	//int terminador
	pid_t child_pid;

	
	
	
		do{
			printf("user-$ ");
			fgets(comando,50,stdin);
	
			removeChar(comando, '\n');
		
			child_pid=fork();
	

			if(child_pid==-1)
				perror("Erro");	
			if(child_pid==0 && strcmp(comando,"exit")!=0){		
				parsing=strtok(comando,limite);

				while(parsing != NULL){
  					parsing = strtok( NULL, limite );
					args[contador] = parsing; 
					contador++;
				}	
			execlp( comando , comando , args[1], args [2] , args[3], args[4] ,NULL);
			perror(comando);
			}
			else wait(child_pid);
		
		}while(strcmp(comando,"exit")!=0);
		

	
	return 0;
}
