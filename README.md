Untappd for Hootsuite
===

Find out more at http://untappdforhootsuite.com
---

To configure this service, set these environment variables:
```sh
export UNTAPPD_CLIENT_ID="[client_id]"          # Your Untappd Client ID
export UNTAPPD_CLIENT_SECRET="[client_secret]"  # Your Untappd Client Secret
export UNTAPPD_TOKEN="[auth_token]"             # A user auth token for authenticated calls
```

To setup third-party front-end libraries, run bower (you may need to install with `sudo npm install -g bower`):
```sh
bower install
```

To run this service, execute the following:

```sh
sbt run
```
