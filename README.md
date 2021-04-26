# Database link
HTTP requests can be issued to [this](https://fake-database-fe-support.herokuapp.com/) link.

# List of available requests 

## Provider (prefix: <strong>/provider</strong>)

<ul>
<li><dl> <strong>GET</strong> requests
    <dt><em>/getAll</em></dt>
    <dd>Returns a list containing all available providers.</dd>
    <dt><em>/getById?id={id}</em></dt>
    <dd>Returns the provider with the specified ID.</dd>
    <dt><em>/getByName?name={name}</em></dt>
    <dd>Returns the provider with the specified name (the first one that matches it).</dd>
    <dt><em>/getInterval?skip={skip}&count={count}</em></dt>
    <dd>Given a skip and count values, it returns a list of COUNT elements skipping SKIP rows from the database.</dd>
    <dt><em>/getCount</em></dt>
    <dd>Returns the number of providers.</dd>
    <dt><em>/searchCount?query={query}</em></dt>
    <dd>Returns the number of providers that contain the QUERY string</dd>
    <dt><em>/search</em></dt>
    <dd>Given a skip and count values, it returns a list of COUNT providers that contain the QUERY string skipping SKIP rows from the database</dd>
</dl></li>

<li><dl> <strong>POST</strong> requests
    <dt><em>/add</em></dt>
    <dd>Alongside a given JSON file which illustrates a provider, the function inserts the given info into the database.</dd>
    <dt><em>/dummy</em></dt>
    <dd>A dummy function which adds fake info into the database (testing purposes only)</dd>
</dl></li>

<li><dl> <strong>PUT</strong> requests
    <dt><em>/update?id={id}&name={name}&credibility={credibility}&avatar={avatar}</em></dt>
    <dd>Updates an already existing provider. The ID is required, but besides this the other info is not.</dd>
</dl></li>

<li><dl> <strong>DELETE</strong> requests
    <dt><em>/delete?id={id}</em></dt>
    <dd>Deletes the provider with the specified ID from the database.</dd>
</dl></li>
</ul>

## User (prefix: <strong>/user</strong>)

<ul>
<li><dl> <strong>GET</strong> requests
    <dt><em>/getAll</em></dt>
    <dd>Returns a list containing all registered users.</dd>
    <dt><em>/get?id={id}</em></dt>
    <dd>Returns the user with the specified ID (if they exist).</dd>
</dl></li>

<li><dl> <strong>POST</strong> requests
    <dt><em>/register</em></dt>
    <dd>Inserts a user specified in JSON format in the request body into the database.</dd>
    <dt><em>/dummy</em></dt>
    <dd>A dummy function which adds fake info into the database (testing purposes only).</dd>
</dl></li>

<li><dl> <strong>PUT</strong> requests
    <dt><em>/update?id={id}&bio={bio}</em></dt>
    <dd>Updates an already existing user's bio (in the future, the password as well).</dd>
</dl></li>

<li><dl> <strong>DELETE</strong> requests
    <dt><em>/delete?id={id}</em></dt>
    <dd>Deletes the user specified by their ID from the database (if they exist).</dd>
    <dt><em>/deleteAll</em></dt>
    <dd>Deletes all the users from the database (testing purposes only).</dd>
</dl></li>
</ul>

## Posts (prefix: <strong>/post</strong>)

<ul>
<li><dl> <strong>GET</strong> requests
    <dt><em>/getAll</em></dt>
    <dd>Returns a list containing all the posts.</dd>
    <dt><em>/getById?id={id}</em></dt>
    <dd>Returns the post with the specified ID (if it exists).</dd>
    <dt><em>/getInterval?skip={skip}&count={count}</em></dt>
    <dd>Skips the first <strong>SKIP</strong> posts and returns the next <strong>COUNT</strong> posts.</dd>
    <dt><em>/getIntervalByProvider?provider_id={}&skip={skip}&count={count}</em></dt>
    <dd>Skips the first <strong>SKIP</strong> posts and returns the next <strong>COUNT</strong> posts from the provider with <strong>PROVIDER_ID</strong>.</dd>
</dl></li>

<li><dl> <strong>POST</strong> requests
    <dt><em>/add</em></dt>
    <dd>Inserts a post specified in JSON format in the request body into the database.</dd>
    <dt><em>/dummy</em></dt>
    <dd>A dummy function which adds fake info into the database (testing purposes only).</dd>
</dl></li>

<li><dl> <strong>DELETE</strong> requests
    <dt><em>/delete?id={id}</em></dt>
    <dd>Deletes the post specified by its ID from the database (if it exists).</dd>
</dl></li>
</ul>

## User preferences (prefix: <strong>/preferences</strong>)

<ul>
<li><dl> <strong>GET</strong> requests
    <dt><em>/getAll</em></dt>
    <dd>Returns a list containing all the rows in the table.</dd>
    <dt><em>/isSubscribed?uid={uid}&prov_id={prov_id}</em></dt>
    <dd>Checks if the a user is subscribed to the provider specified by ID.</dd>
    <dt><em>/getByUserId?uid={uid}&skip={skip}&count={count}</em></dt>
    <dd>Skips the first <strong>SKIP</strong> providers in the subscription list of the specified user and returns the next <strong>COUNT</strong> providers. If not specified, the <strong>SKIP</strong> parameter defaults to 0, and the <strong>COUNT</strong> parameter to 100.</dd>
</dl></li>

<li><dl> <strong>POST</strong> requests
    <dt><em>/subscribeUserToProviders?uid={uid}</em></dt>
    <dd>Inserts an entry into the table for each provider ID in the request body. Sets the subscription status for all specified IDs to true.</dd>
</dl></li>

<li><dl> <strong>PUT</strong> requests
    <dt><em>/updateSubscriptionStatus?uid={uid}&prov_id={prov_id}&status={status}</em></dt>
    <dd>Updates the subscription status for the specified user and provider.</dd>
</dl></li>
</ul>

# Other mentions

## Provider POST request body format

```json
{
  "name": "digi",
  "credibility": 90.0,
  "avatar": "www.example.com"
}
```

## User POST request body format

```json
{
  "username": "gigi",
  "avatarUrl": "www.example.com",
  "bio": "I am gigi",
  "email": "gigi@yahoo.com"
}
```

## Posts POST request body format

```json
{
  "thumbnail": "sampleThumbnail",
  "title": "sameTitle",
  "description": "sampleDescription",
  "postDate": "yyyy-MM-dd'T'HH:mm:ss.SSSX"/"yyyy-MM-dd'T'HH:mm:ss.SSS"/"EEE, dd MMM yyyy HH:mm:ss zzz"/"yyyy-MM-dd", // choose any of these formats
  "score": 15.0,
  "sourceUrl": "www.example.com"
}
```

## User preferences POST request body format

```json
[1, 2, 3, 4] // IDs of the providers
```
