From python:3.7.5-slim
RUN pip install redis==3.3.8
WORKDIR /src
COPY ./src/run.py /src/
CMD ["tail", "-f", "/dev/null"]