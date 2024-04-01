"use client";

import { QueryClient, QueryClientProvider } from "react-query";

const makeQueryClient = () => {
  return new QueryClient({
    defaultOptions: {
      queries: {
        staleTime: 60 * 1000,
      },
    },
  });
};

let browserQueryClient: QueryClient | undefined = undefined;

const getQueryClient = () => {
  if (typeof window === "undefined") {
    return makeQueryClient();
  } else {
    if (!browserQueryClient) browserQueryClient = makeQueryClient();
    return browserQueryClient;
  }
};

const Providers = ({ children }: any) => {
  const queryClient = getQueryClient();

  return (
    <QueryClientProvider client={queryClient}>{children} </QueryClientProvider>
  );
};

export default Providers;
