---
name: commit-message
description: Stage relevant changed files and commit them using this project's commit message style — short, present tense, no co-author trailer. Use whenever the user asks to commit changes in this repo, or invokes /commit-message directly.
---

# Commit Workflow

When invoked, run the commit end to end, without pausing for message approval:

1. Run `git status` and `git diff` to see what changed.
2. Stage the specific relevant files by name (never `git add -A`); flag anything that looks like a secret before staging it.
3. Draft the commit message yourself following the style rules below — do not ask the user to supply their own message.
4. Run `git commit` with that message, then confirm with `git status`.

# Commit Message Style

When drafting a commit message for this repository, follow these rules exactly:

- **Length**: no more than two short sentences total.
- **Voice**: present tense ("Add", "Fix", "Rename" — not "Added", "Fixed", "Renamed").
- **Content**: first sentence states what changed; an optional second sentence states why, only if the reason isn't obvious from the diff.
- **No co-author trailer**: never add `Co-Authored-By` or any Claude/Anthropic attribution line.
- **No emojis.**

## Example

```
Use UUID instead of String for event id.

Avoids committing to a weak string type before the JPA @Id decision in Phase 5.
```

Apply this style any time the user asks for a commit in this repo, without needing to be reminded of these rules.
