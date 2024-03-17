import { useEffect, useState } from 'react';
import './App.css';
import { Octokit } from 'octokit';

function App() {
  const [commits, setCommits] = useState(null);

  //TODO:
  // Access Token은 각자 발급..
  const octokit = new Octokit({ auth: process.env.REACT_APP_GITHUB_TOKEN });

  useEffect(() => {

    //TODO:
    // octokit.rest을 하나의 모듈로 지정해서 사용하고싶음.
    const listCommits = octokit.rest.repos.listCommits({
      owner: "Kimtaewon9483",
      repo: "daily-study",
      per_page: 100,
    });

    // 비동기 함수를 콜백으로 처리하기엔 힘드니 async await로 함수화 시키고싶음.
    listCommits.then(res => res)
    .then(data => {
      
      if (!data) return;

      const commitList = data.data.map(item => item.commit);
      setCommits(commitList);
    })
    .catch(err => {
      throw new Error(err);
    });

  }, []);

  return (
    <div className="App">
      <ul>
        {
          commits && commits.map(commit => (
            <li key={commit.tree.sha} style={{ display: "flex", marginBottom: "10px" }}>
              <div style={{ borderRight: "1px solid black", paddingRight: "10px", marginRight: "10px" }}>{commit.author.name}</div>
              <div>{commit.message}</div>
            </li>
          ))
        }
      </ul>
    </div>
  );
}

export default App;
