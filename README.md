## Model
# pubsub-choreography-with-idempotency
# 중복실행 방지 적용
https://labs.msaez.io/#/189596125/storming/0b9f7fe19efd77c20c1d40f546920853

![스크린샷 2025-06-11 100806](https://github.com/user-attachments/assets/b66f29ad-ccce-4464-b3f5-436951c94654)
![스크린샷 2025-06-11 115410](https://github.com/user-attachments/assets/d3727b6e-58b5-4339-bdb2-dc04e3745c0e)
![스크린샷 2025-06-11 115425](https://github.com/user-attachments/assets/c72a03d3-60ff-44d9-b7e2-35f3620abfaf)
![스크린샷 2025-06-11 120008](https://github.com/user-attachments/assets/336b60eb-7a02-444f-a29f-5ad28569dcd3)

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
