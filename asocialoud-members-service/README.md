# asocialoud-members-service
Members backend module

docker build --tag=asocialoud-members-service:latest --rm=true .

docker volume create --name=asocialoud-member-service-volume

docker run --name=asocialoud-member-service --publish=7070:8070  --volume=asocialoud-member-service-volume:/var/lib/asocialoud-member-service-volume asocialoud-members-service:latest