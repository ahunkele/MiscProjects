#include <stdio.h>
#include <stdlib.h>




struct Node {
    int data;
    struct Node* next;
};

struct Node* head; 

void Insert(int i){
	struct Node* temp = (struct Node*)malloc(sizeof(struct Node));
	temp->data = i;
	temp->next = head;
	head = temp;
}

void Print(){
	struct Node* temp = head;
	while(temp != NULL){
		printf("%d ->", temp->data);
		temp = temp->next;
	}
}

int main(){
	
    int running = 1;
	head = NULL;
	int pick;
	printf("Enter your option: \n 1. Add element \n 2. Search \n 3. Display \n 4. Exit \n");
	scanf("%d", &pick);
	Insert(4);
	Insert(20);

	while(running=1){
		if (pick == 1) {
			int val;
			printf("Enter a value to add to the list: \n");
			scanf("%d", &val);
			Insert(val);
			printf("%d Inserted /n", val);
			
		}

		if (pick == 2) {
			printf("Enter a value to search for in the list: \n");
		}

		if (pick == 3) {
			Print();
		
		}

		if (pick == 4) {
			running = 0;
		}
	}
	

	
	
	return 0;
}