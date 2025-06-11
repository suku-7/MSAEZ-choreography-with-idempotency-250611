## Model
# pubsub-choreography-with-idempotency (중복실행 방지 적용)
https://labs.msaez.io/#/189596125/storming/0b9f7fe19efd77c20c1d40f546920853

![스크린샷 2025-06-11 100806](https://github.com/user-attachments/assets/b66f29ad-ccce-4464-b3f5-436951c94654)
![스크린샷 2025-06-11 115410](https://github.com/user-attachments/assets/d3727b6e-58b5-4339-bdb2-dc04e3745c0e)
![스크린샷 2025-06-11 115425](https://github.com/user-attachments/assets/c72a03d3-60ff-44d9-b7e2-35f3620abfaf)
![스크린샷 2025-06-11 120008](https://github.com/user-attachments/assets/336b60eb-7a02-444f-a29f-5ad28569dcd3)

# 터미널 작성 참고용
1. sdk install java
2. lombok 1.18.30으로 수정
- order, delivery, product 3개 다 수정해야함

3. kafka 모니터링 화면 띄우기
- cd kafka
- docker-compose exec -it kafka /bin/bash
- cd /bin
- ./kafka-console-consumer --bootstrap-server localhost:9092 --topic choreography.with.idempotency

4. order 실행
- cd order/
- mvn spring-boot:run

5. delivery 실행
- cd delivery/
- mvn spring-boot:run

6. product 실행
- cd product/
- mvn spring-boot:run

7. 데이터 넣어서 중복실행 확인.
- http :8083/inventories productName=TV stock=1000
- http :8083/inventories productName=RADIO stock=1000

- http :8081/orders customerId=1 productId=1 productName=TV qty=10

- http :8081/orders/1
- http :8083/inventories

- http :8081/orders customerId=1 productId=2 productName=TV qty=1002
- 중복실행시켜서 2002개가 되어버렸다.

8. product를 다시 내렸다가 올려보기 주문을 다시 하기
- mvn spring-boot:run
- http :8083/inventories productName=TV stock=1000
- http :8083/inventories productName=RADIO stock=1000
- http :8081/orders customerId=1 productId=1 productName=TV qty=10
- http :8081/orders/1
- http :8083/inventories
- http :8081/orders customerId=1 productId=2 productName=TV qty=1002
- "REJECTED DUE TO INVENTORY ERROR" 발생시킴

## Before Running Services
### Make sure there is a Kafka server running
```
cd kafka
docker-compose up
```
- Check the Kafka messages:
```
cd infra
docker-compose exec -it kafka /bin/bash
cd /bin
./kafka-console-consumer --bootstrap-server localhost:9092 --topic
```

## Run the backend micro-services
See the README.md files inside the each microservices directory:

- order
- inventory


## Run API Gateway (Spring Gateway)
```
cd gateway
mvn spring-boot:run
```

## Test by API
- order
```
 http :8088/orders id="id"productId="productId"qty="qty"customerId="customerId"amount="amount"status="status"address="address"
```
- inventory
```
 http :8088/inventories id="id"stock="stock"
```


## Run the frontend
```
cd frontend
npm i
npm run serve
```

## Test by UI
Open a browser to localhost:8088

## Required Utilities

- httpie (alternative for curl / POSTMAN) and network utils
```
sudo apt-get update
sudo apt-get install net-tools
sudo apt install iputils-ping
pip install httpie
```

- kubernetes utilities (kubectl)
```
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
```

- aws cli (aws)
```
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install
```

- eksctl 
```
curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
sudo mv /tmp/eksctl /usr/local/bin
```
