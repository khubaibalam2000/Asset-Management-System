const express = require('express')
const {graphqlHTTP } = require('express-graphql')
const { MongoClient } = require('mongodb');

const {
  GraphQLSchema,
  GraphQLObjectType,
  GraphQLString,
  GraphQLList,
  GraphQLInt,
  GraphQLNonNull
} = require('graphql')
const app = express()

// Asset Schema Type
const AssetType = new GraphQLObjectType({
  name: 'Asset',
  description: 'This represents an asset',
  fields: () => ({
    id: { type: GraphQLNonNull(GraphQLInt) },
    type: { type: GraphQLNonNull(GraphQLString) },
    location: { type: GraphQLNonNull(GraphQLString) },
    threat: { type: GraphQLNonNull(GraphQLString) },
    level: { type: GraphQLNonNull(GraphQLInt) },
    currentdefense: { type: GraphQLNonNull(GraphQLString) },
    proposeddefense: { type: GraphQLNonNull(GraphQLString) }
  })
})

lowDefense = ['password', 'pin', 'OTP', 'updates']
highDefense = ['encryption', 'firewall', 'MFA']
// Root Query
const RootQueryType = new GraphQLObjectType({
  name: 'Query',
  description: 'Root Query',
  fields: () => ({
    assetLow: {
      type: new GraphQLList(AssetType),
      description: 'Assets with low defense but higher importance',
      resolve: () => assets.filter(asset => asset.level >= 8 && lowDefense.includes(asset.currentdefense))
    },
    assetHigh: {
      type: new GraphQLList(AssetType),
      description: 'Assets with higher defense but low importance',
      resolve: () => assets.filter(asset => asset.level <= 5 && highDefense.includes(asset.currentdefense))
    },
    assets: {
      type: new GraphQLList(AssetType),
      description: 'List of All Matching assets',
      resolve: () => assets
    }
  })
})


const schema = new GraphQLSchema({
  query: RootQueryType
})

app.use('/graphql', graphqlHTTP({
  schema: schema,
  graphiql: true
}))


// Connection to MongoDB Atlas
let uri =
  `mongodb+srv://kukudb:kukudb@my-cluster.gdjpc4s.mongodb.net/?retryWrites=true&w=majority`;
const client = new MongoClient(uri);
assets = []
async function run() {
  try {
    await client.connect();
    const database = client.db("My-Cluster");
    const assetsDb = database.collection("assets");
    const cursor = assetsDb.find();
    await cursor.forEach(doc => assets.push(doc));
  } finally {
    await client.close();
  }
}
run().catch(console.log);

// Server Starting
app.listen(5000, () => console.log('Server Running'))
