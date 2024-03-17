import { Octokit } from "octokit"; 

const OCTOKIT_TOKEN = process.env.REACT_APP_GITHUB_TOKEN;

if (!OCTOKIT_TOKEN) {
  throw new Error('.env.local파일의 토큰값을 확인해주세요');
}

const octokit = new Octokit({
  auth: OCTOKIT_TOKEN,
});

export const getListCommits = (owner, repo) => octokit.rest.repos.listCommits({
  owner,
  repo,
  per_page: 100
});

export const getAllCollaborators = (owner, repo) => octokit.rest.repos.listCollaborators({
  owner,
  repo,
});