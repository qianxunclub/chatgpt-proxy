commit_id=$(git rev-parse HEAD)
mvn clean compile package -B -DskipTests
docker build -t chatgpt-proxy:"${commit_id}" .
docker tag chatgpt-proxy:"${commit_id}" 960339491/chatgpt-proxy:"${commit_id}"
docker tag chatgpt-proxy:"${commit_id}" 960339491/chatgpt-proxy:latest