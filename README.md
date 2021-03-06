# Gitique

## This project is deprecated

GitHub [added this feature][ghblog], along with many other excellent code review tools!

- - -

[![Build status](https://travis-ci.org/smcgivern/gitique.svg?branch=master)][travis]

## Contents

* [Overview](#overview)
* [Usage](#usage)
  * [Icon key](#icon-key)
* [Developing](#developing)
  * [Production builds](#production-builds)

## Overview

GitHub's pull requests are fine for code review most of the time, but can get messy for
long-lived PRs where there are lots of commits touching lots of files, but the new commits
are making relatively small changes. It's possible to add comments to individual commits,
but this still has the problems that a) the commit comments show up differently to regular
PR diff comments and b) sometimes a range of commits have been added to be reviewed, and
reviewing them one-by-one can be tedious.

Gitique enables the files tab of a GitHub PR to be restricted to only a set of the most
recent commits. By default, it chooses all commits since the last comment by a reviewer
(anyone who's not the author).

The gif below shows what happens with a [demo PR][demo-pr]: some changes were made, a
reviewer made a comment, and the author added a commit. Choosing to view only the latest
changes hides all changes in `.gitignore` (as the last commit didn't touch that file) and
shows only the relevant changes from the latest commit in green (so line 3 of the README
is no longer marked as a new line).

[![Demo gif](/doc/demo.gif)][demo-pr]

For more on this subject, see [improving GitHub code review][rs-blog-post].

## Usage

Once installed, Gitique runs on every GitHub page. When it detects that you're on a pull
request, it looks at the discussion. Any commits made after the last comment by someone
who is not the author will have a green icon next to them. Those commits will have their
contents kept visible when Gitique is enabled, while the contents of other commits will be
hidden.

The actual change in behaviour is on the files tab, where Gitique adds a button to view
only the changes from the selected commits. Any changes for the overall diff which are not
included in the restricted diff will be hidden.

To change the commits Gitique uses, click on the icon next to a commit SHA. This will set
the commit as the base commit for the comparison. (This commit's contents will _not_ be
included in the Gitique view, but all commits following it will be.)

### Icon key

* ![Very light grey](https://cdn.rawgit.com/smcgivern/gitique/v0.2.0-screenshots/doc/icons/default.svg) -
  a commit that is not the HEAD, when there are no review comments.
* ![Light grey](https://cdn.rawgit.com/smcgivern/gitique/v0.2.0-screenshots/doc/icons/reviewed.svg) -
  a reviewed commit. If there are comments by reviewers (people not the author of the pull
  request), all but the last commit before the last review comment will have this
  colour. Diffs from these commits are not shown when the Gitique view is enabled.
* ![Black](https://cdn.rawgit.com/smcgivern/gitique/v0.2.0-screenshots/doc/icons/base.svg) -
  the base commit. This takes the place of the HEAD of the target branch when the Gitique
  view is enabled, so its diff is not shown. By default, it is the last commit before the
  last review comment.
* ![Light green](https://cdn.rawgit.com/smcgivern/gitique/v0.2.0-screenshots/doc/icons/new.svg) -
  a new commit. All but the last commit after the last reviewer comment are shown in this
  colour by default, and their diffs are maintained when the Gitique view is enabled.
* ![Dark green](https://cdn.rawgit.com/smcgivern/gitique/v0.2.0-screenshots/doc/icons/head.svg) -
  the HEAD of the selected branch in this PR. This diff will always be shown when the
  Gitique view is enabled, and this is the only commit which cannot be selected as the
  base commit.

## Developing

1. Clone this repo.
2. From the root of the cloned repo, run `make`. This will download the ClojureScript
   compiler and run it on the source, producing the file `gitique.js`. This script will
   watch for changes in the source.
3. This depends on the browser.
   1. Chrome: go to [chrome://extensions/](chrome://extensions/) and choose to load an
      unpacked extension, then point to the [`src/chrome`](src/chrome) directory in the
      cloned repo.
   2. Firefox: install `jpm` (`npm install -g jpm`) first, then from
      [`src/firefox`](src/firefox) do `jpm run` and use the blank profile that generates.
      Alternatively, run `jpm xpi` and then choose to install an add-on from a file at
      [about:addons](about:addons).
4. To run the unit tests, run `make test`. This requires
   [PhantomJS](http://phantomjs.org/) version 2 to be on the path as `phantomjs`. It will
   watch the `test` and `src` directories and run the tests on the built output. `make ci`
   will compile, run the tests, and exit without watching.
5. To run the Webdriver tests, install [boot](http://boot-clj.com/) and run `make
   selenium`. This will run some basic tests against the [gitique-examples repo][examples]
   in Chrome and Firefox. A [production build](#production-builds) is needed for these, as
   the generated XPI is loaded by Firefox for the tests.

### Production builds

1. Update [`dist/chrome/manifest.json`](dist/chrome/manifest.json) and
   [`dist/firefox/package.json`](dist/firefox/package.json) for the new version.
2. Run `make release`. This will build using advanced optimizations, then package
   `dist/chrome` into `dist/chrome.zip`, and `dist/firefox` into
   `dist/firefox/@gitique-$version.xpi`.

[travis]: https://travis-ci.org/smcgivern/gitique
[demo-pr]: https://github.com/smcgivern/gitique-examples/pull/2
[examples]: https://github.com/smcgivern/gitique-examples
[rs-blog-post]: http://eng.rightscale.com/2015/10/21/improving-github-code-review.html
[ghblog]: https://github.com/blog/2123-more-code-review-tools
