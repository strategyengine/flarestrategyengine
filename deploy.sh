mvn clean compile install
docker build -t flarestrategyengine .
docker tag assetpriceservice gcr.io/flarestrategyengine/flarestrategyengine
docker push gcr.io/flarestrategyengine/flarestrategyengine