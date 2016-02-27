---
title: "CSS vs. XPath, under a microscope"
slug: "33-xpath-vs-css-revisited"
number: 33
publish_date: 2014-01-14
tags:
  - "selectors"
  - "xpath"
  - "css"
  - "css_selectors"
  - "locators"
  - "locator_strategy"
level: 2
category: "benchmarks"
---

__NOTE: This is part 2 of 3. For the first installment, [go here](http://elementalselenium.com/tips/32-xpath-vs-css). For the last installment, go [here](http://elementalselenium.com/tips/34-xpath-vs-css-revisited-2).__

## The Problem

In a previous tip we reviewed [the performance of CSS vs. XPath against Sauce Labs](http://elementalselenium.com/tips/32-xpath-vs-css). While it demonstrated that each locator strategy is reasonably equivalent across browsers, it didn't adequately paint the whole picture for us.

Given that it was an end-to-end test there were a lot of variables at play -- Sauce startup, browser start up, and latency to and from the application under test. The unfortunate takeaway from that experiment could be that one driver may be faster than the other (e.g. IE vs Firefox), when in fact, that's not the case at all.

## A Solution

To get a real taste of what the performance difference is between CSS and XPath, we will need to dig deeper. We'll do that by running everything from a local machine while using a performance benchmarking utility. We'll also want to focus on a specific Selenium action rather than the entire test run, and run things numerous times.

## An Example

For this example, I set up a local Windows XP virtual machine. This was quick work thanks to the images made available by Microsoft. You can grab one [here](http://www.modern.ie/en-us/virtualization-tools#downloads) and see [a write-up walking your through setup in Virtual Box](http://webees.me/installing-virtualbox-for-mac-and-set-up-modern-ie/).

After [installing Ruby (1.9.3)](http://rubyinstaller.org/downloads/) I installed all available browsers and their equivalent browser drivers for Selenium use. You can learn more about how to set up different browsers to run locally in Selenium [here](http://elementalselenium.com/tips/29-chrome-driver).

For benchmarking I used the library that's available in Ruby's standard lib called 'benchmark'. You can learn more about it [here](http://rubylearning.com/blog/2013/06/19/how-do-i-benchmark-ruby-code/).

In order to get an adequate sample set of data, I ran the same test against each browser 100 times. And to weed out anomalies in the data I used the rehearsal feature of benchmark so that it would run the full test sequence, perform garbage collection, and then run it again.

To make things comparable, a few of the locators were updated to make for better matches in comparison to each other. And the specific action we measured is `find_element`. You can see the locators and test code used [here](https://github.com/tourdedave/selenium-benchmark/blob/master/lib/page-objects/small_dom.rb).

Here are the results.

## The Results

__NOTE: The output is in seconds, and the results are for the total run time of 100 executions.__

[![local browser benchmarks](/img/local-browser-benchmarks.png)](http://elementalselenium.com/img/local-browser-benchmarks.png)

## What The Results Tell Us

On a whole, Internet Explorer is slower than the other drivers, but between CSS and XPath it looks like XPath is actually __faster__ than CSS. Chrome and Opera have some differences, albeit much smaller, but they sway in both directions. In some cases CSS is faster, and in others, XPath. And Firefox looks to be a bit more optimized for CSS since it's mostly faster across the board.

__NOTE: Opera 12.16 was used since that is the last supported version within the Selenium project. That's because they switched to using Chromium and dropped Selenium support. That and Mozilla hired Opera's only WebDriver developer -- so, there's that.__

## Outro

Even with these speed differences they are only a few seconds (or fractions of seconds) apart -- and that's for 100 executions. When you think about how it takes 30 seconds or more to complete a test run, this kind of difference is negligible.

As I noted in [my previous tip](http://elementalselenium.com/tips/32-xpath-vs-css), the choice between CSS and XPath can be a tough one to make. But now you are armed with more than enough data to make the choice for yourself. It's really just a matter of finding what works for you and your team and to not get weighed down by the hype and opinions around which one is better.

Happy Testing!

## Acknoledgements

I'd like to give a huge shout out to [David Burns](https://twitter.com/AutomatedTester) and [Brian Goad](https://twitter.com/bbbco)! They helped make this tip possible by each helping in their own way. David for his feedback and guidance on [how to do effective performance testing](https://www.youtube.com/watch?v=qQgDDAan4rM). And Brian for his feedback, recommendations, and [code contributions](https://twitter.com/bbbco/status/421707164919418880). Thank you!
