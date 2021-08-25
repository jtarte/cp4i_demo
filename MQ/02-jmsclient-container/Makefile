# help: clean                          - clean all files
.PHONY: clean
clean:
	@mvn clean

# help: build                          - build exec jar
.PHONY: build
build:
	@mvn assembly:single

# help: serve                          - serve project html documentation
.PHONY: docker
docker:
	@docker build -t jmsclient:latest .
	


